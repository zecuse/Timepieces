package com.zecuse.timepieces.ui.view.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R

@Composable
fun HandButton(left: Boolean = true, onClick: () -> Unit)
{
	val leftHand = stringResource(R.string.left_hand)
	val rightHand = stringResource(R.string.right_hand)
	Box(modifier = Modifier
		.clickable {onClick()}
		.semantics {stateDescription = if (left) leftHand else rightHand}) {
		if (left)
		{
			Icon(painter = painterResource(R.drawable.baseline_back_hand_24),
			     contentDescription = null,
			     tint = MaterialTheme.colorScheme.primary,
			     modifier = Modifier
				     .size(50.dp)
				     .scale(scaleX = 1f,
				            scaleY = 1f))
			Text(text = "L",
			     color = MaterialTheme.colorScheme.onPrimary,
			     style = MaterialTheme.typography.titleLarge,
			     modifier = Modifier.offset(x = 21.dp,
			                                y = 23.dp))
		}
		else
		{
			Icon(painter = painterResource(R.drawable.baseline_back_hand_24),
			     contentDescription = null,
			     tint = MaterialTheme.colorScheme.primary,
			     modifier = Modifier
				     .size(50.dp)
				     .scale(scaleX = -1f,
				            scaleY = 1f))
			Text(text = "R",
			     color = MaterialTheme.colorScheme.onPrimary,
			     style = MaterialTheme.typography.titleLarge,
			     modifier = Modifier.offset(x = 16.dp,
			                                y = 23.dp))
		}
	}
}