package ayush.foodie.presentation.components

import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ayush.foodie.presentation.components.util.ShimmerAnimationDefinitions

@Composable
fun LoadingListShimmer(
    cardHeight: Dp,
    padding: Dp = 16.dp
) {
    WithConstraints() {

        val cardWidthPx = with(AmbientDensity.current) {
            ((maxWidth - padding * 2).toPx())
        }


        val cardHeightPx = with(AmbientDensity.current) {
            ((cardHeight - padding).toPx())
        }

        val cardAnimationDefinitions = remember {
            ShimmerAnimationDefinitions(
                widthPx = cardWidthPx,
                heightPx = cardHeightPx
            )
        }

        val cardShimmerTranslateAnimation = transition(
            definition = cardAnimationDefinitions.shimmerTransitionDefinition,
            initState = ShimmerAnimationDefinitions.ShimmerAnimationState.START,
            toState = ShimmerAnimationDefinitions.ShimmerAnimationState.END
        )

        val colors = listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.9f),
        )

        val xCardShimmer = cardShimmerTranslateAnimation[cardAnimationDefinitions.xShimmerPropKey]
        val yCardShimmer = cardShimmerTranslateAnimation[cardAnimationDefinitions.yShimmerPropKey]

        ScrollableColumn {
            repeat(5) {
                ShimmerRecipeCardItem(
                    colors = colors,
                    cardHeight = cardHeight,
                    xShimmer = xCardShimmer,
                    yShimmer = yCardShimmer,
                    padding = padding,
                    gradientWidth = cardAnimationDefinitions.gradientWidth
                )
            }
        }


    }
}