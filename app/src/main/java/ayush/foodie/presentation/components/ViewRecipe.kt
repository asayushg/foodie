package ayush.foodie.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import ayush.foodie.R
import ayush.foodie.domain.model.Recipe
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun ViewRecipe(
    recipe: Recipe,
) {

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxSize(),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        ScrollableColumn {
            recipe.featuredImage?.let { url ->

                GlideImage(
                    data = url,
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(225.dp),
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    },
                    error = {
                        Image(bitmap = imageResource(R.drawable.empty_plate))
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    recipe.title?.let { title ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = title,
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .wrapContentWidth(Alignment.Start),
                                style = MaterialTheme.typography.h2
                            )
                            val rank = recipe.rating.toString()
                            Text(
                                text = rank,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.End)
                                    .align(Alignment.CenterVertically),
                                style = MaterialTheme.typography.h5
                            )
                        }
                    }
                    recipe.publisher?.let { publisher ->
                        val updated = recipe.dateUpdated
                        Text(
                            text = if (updated != null) {
                                "Updated $updated by $publisher"
                            } else {
                                "By $publisher"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.caption
                        )
                    }
                    recipe.description?.let { description ->
                        if (description != "N/A") {
                            Text(
                                text = description,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                    for (ingredient in recipe.ingredients!!) {
                        Text(
                            text = ingredient,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    recipe.cookingInstructions?.let { instructions ->
                        Text(
                            text = instructions,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }

    }

}