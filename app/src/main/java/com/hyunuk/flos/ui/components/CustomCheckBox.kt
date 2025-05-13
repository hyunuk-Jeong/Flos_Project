package com.hyunuk.flos.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckBox(
    label : String,
    checked:Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onCheckedChange(!checked) } // 텍스트/체크박스 모두 눌리게
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null // 클릭 동작은 Row에서 처리
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}