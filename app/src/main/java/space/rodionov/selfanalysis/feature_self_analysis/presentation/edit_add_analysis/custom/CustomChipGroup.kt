package space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import space.rodionov.selfanalysis.feature_self_analysis.presentation.edit_add_analysis.CustomChip


@Preview(showBackground = true)
@Composable
fun CustomChipGroup(
    emotions: List<String> = listOf("Mad", "Glad", "Lonely", "Scared", "Sad"),
    selectedEmotion: String? = null,
    onSelectedChanged: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        FlowRow(
            mainAxisSpacing = 16.dp,
            crossAxisSpacing = 16.dp,
        ) {
            emotions.forEach {
                CustomChip(
                    name = it,
                    isSelected = selectedEmotion == it,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    }
                )
            }
        }
    }
}