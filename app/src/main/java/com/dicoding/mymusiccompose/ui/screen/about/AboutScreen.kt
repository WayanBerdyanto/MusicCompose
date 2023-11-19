package com.dicoding.mymusiccompose.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.mymusiccompose.R
import com.dicoding.mymusiccompose.ui.theme.MyMusicComposeTheme

@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
) {
    AboutContent(
        image = R.drawable.img,
        name = stringResource(R.string.myName),
        email = stringResource(R.string.myEmail),
        university = stringResource(R.string.myUniversity),
        onBackClick = navigateBack
    )
}

@Composable
fun AboutContent(
    image: Int,
    name: String,
    email: String,
    university: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var aspectRatio by remember { mutableStateOf(16f / 12f) }
    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(aspectRatio)
                    .onGloballyPositioned { layoutResult ->
                        val width = layoutResult.size.width
                        val height = layoutResult.size.height
                        aspectRatio = if (width > height) {
                            width / height.toFloat()
                        } else {
                            height / width.toFloat()
                        }
                    }
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                        .align(Alignment.TopStart)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = modifier
                        .padding(8.dp)
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = university,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun AboutScreenPreview() {
    MyMusicComposeTheme {
        AboutContent(
            image = R.drawable.img,
            name = "Wayan Berdyanto",
            email = "aber.panak.atok@gmail.com",
            university = "Duta Wacana Christian University",
            onBackClick = {}
        )
    }
}