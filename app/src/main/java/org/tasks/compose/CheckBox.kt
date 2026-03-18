package org.tasks.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import org.tasks.data.entity.Task
import org.tasks.kmp.org.tasks.themes.ColorProvider.priorityColor
import org.tasks.ui.CheckBoxProvider.Companion.getCheckboxRes

@Composable
fun CheckBox(
    task: Task,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CheckBox(
        isCompleted = task.isCompleted,
        isRecurring = task.isRecurring,
        priority = task.priority,
        onCompleteClick = onCompleteClick,
        modifier = modifier,
    )
}

@Composable
fun CheckBox(
    isCompleted: Boolean,
    isRecurring: Boolean,
    priority: Int,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scale = remember { Animatable(1f) }
    val previouslyCompleted = remember { mutableStateOf(isCompleted) }

    LaunchedEffect(isCompleted) {
        if (isCompleted && !previouslyCompleted.value) {
            scale.animateTo(
                targetValue = 1.3f,
                animationSpec = tween(durationMillis = 100, easing = FastOutLinearInEasing),
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            )
        }
        previouslyCompleted.value = isCompleted
    }

    IconButton(onClick = onCompleteClick, modifier = modifier) {
        Icon(
            painter = painterResource(id = getCheckboxRes(isCompleted, isRecurring)),
            tint = Color(
                priorityColor(
                    priority = priority,
                    isDarkMode = isSystemInDarkTheme(),
                )
            ),
            contentDescription = null,
            modifier = Modifier.scale(scale.value),
        )
    }
}