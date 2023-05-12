package com.example.nycschools.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.nycschools.response.NYCSchoolItem

@Composable
fun displayNYCSchoolItem(
    nycSchoolItem: NYCSchoolItem,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        nycSchoolItem.school_name?.let { schoolName ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = schoolName,
                    modifier = Modifier.align(alignment = Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}

@Composable
fun displayNYCSchoolDetails(nycSchoolItem: NYCSchoolItem?) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
        ) {
            nycSchoolItem?.sat_critical_reading_avg_score?.let { satScore ->
                Text(
                    text = "Critical Reading Avg Score : ".plus(satScore),
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.h5
                )
            }
            nycSchoolItem?.sat_math_avg_score?.let { satMathScore ->
                Text(
                    text = "Math Avg Score : ".plus(satMathScore),
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.h5
                )
            }
            nycSchoolItem?.sat_writing_avg_score?.let { satWritingScore ->
                Text(
                    text = "Writing Avg Score : ".plus(satWritingScore),
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}