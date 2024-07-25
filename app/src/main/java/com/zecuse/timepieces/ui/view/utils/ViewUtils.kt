package com.zecuse.timepieces.ui.view.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zecuse.timepieces.R
import com.zecuse.timepieces.ui.theme.TimepiecesTheme

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
			Icon(painter = painterResource(R.drawable.back_hand_24),
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
			Icon(painter = painterResource(R.drawable.back_hand_24),
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

@Composable
fun SwipeSettings(modifier: Modifier = Modifier,
                  horizontal: Boolean = false,
                  onClick: () -> Unit)
{
	val size = 30.dp
	val leftMod = if (horizontal) Modifier.rotate(90f) else Modifier
	val rightMod = if (horizontal) Modifier.rotate(-90f) else Modifier
	Row(modifier = modifier.clickable {onClick()}) {
		Icon(painter = painterResource(R.drawable.double_arrow_24),
		     contentDescription = null,
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = leftMod.size(size))
		Icon(painter = painterResource(R.drawable.settings_24),
		     contentDescription = null,
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = Modifier.size(size))
		Icon(painter = painterResource(R.drawable.double_arrow_24),
		     contentDescription = null,
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = rightMod.size(size))
	}
}

@Composable
fun SwipeContent(icon: Int,
                 modifier: Modifier = Modifier,
                 horizontal: Boolean = false,
                 onClick: () -> Unit)
{
	val size = 30.dp
	val leftMod = if (horizontal) Modifier.rotate(90f) else Modifier
	val rightMod = if (horizontal) Modifier.rotate(-90f) else Modifier
	Row(modifier = modifier.clickable {onClick()}) {
		Icon(painter = painterResource(R.drawable.double_arrow_24),
		     contentDescription = null,
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = leftMod
			     .size(size)
			     .scale(scaleX = 1f,
			            scaleY = -1f))
		Icon(painter = painterResource(icon),
		     contentDescription = null,
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = Modifier.size(size))
		Icon(painter = painterResource(R.drawable.double_arrow_24),
		     contentDescription = null,
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = rightMod
			     .size(size)
			     .scale(scaleX = 1f,
			            scaleY = -1f))
	}
}

@Preview
@Composable
private fun Above()
{
	TimepiecesTheme {
		Column {
			HandButton {}
			SwipeSettings(horizontal = true) {}
		}
	}
}