package space.rodionov.selfanalysis.feature_self_analysis.presentation.analysis_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import space.rodionov.porosenokpetr.ui_compose.theme.SelfAnalysisTheme
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis

@Composable
fun AnalysisItem(
    analysis: Analysis,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = analysis.situation,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.widthIn(min = 32.dp)
                .align(Alignment.Top),
            text = analysis.date,
            fontSize = 14.sp,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}