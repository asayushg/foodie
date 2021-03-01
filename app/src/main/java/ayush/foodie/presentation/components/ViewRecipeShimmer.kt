package ayush.foodie.presentation.components

import androidx.compose.animation.transition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ayush.foodie.presentation.components.util.ShimmerAnimationDefinitions

@Composable
fun ViewRecipeShimmer(
    cardHeight: Dp,
    padding: Dp = 16.dp,
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

        val xShimmer = cardShimmerTranslateAnimation[cardAnimationDefinitions.xShimmerPropKey]
        val yShimmer = cardShimmerTranslateAnimation[cardAnimationDefinitions.yShimmerPropKey]


        val brush = Brush.linearGradient(
            colors,
            start = Offset(
                xShimmer - cardAnimationDefinitions.gradientWidth,
                yShimmer - cardAnimationDefinitions.gradientWidth
            ),
            end = Offset(xShimmer, yShimmer)
        )

        Column(
            modifier = Modifier.padding(padding)
        ) {
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)
                        .background(brush = brush)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight / 10)
                        .background(brush = brush)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight / 10)
                        .background(brush = brush)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight / 10)
                        .background(brush = brush)
                )
            }
        }
    }
}