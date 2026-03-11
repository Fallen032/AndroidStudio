package com.example.parent_dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parent_dashboard.ui.theme.Parent_DashboardTheme

/**
 * THEME & STYLING SECTION
 * Change these values to update the global look of your dashboard.
 */
// Define the font family. Currently using Default as fallback.
// If you add .ttf files to res/font, you can reference them here using Font(R.font.your_font_name).
val RadioCanadaBig = FontFamily.Default

// Custom Colors from the design. Update these Hex codes to change the brand colors.
val DashboardGreen = Color(0xFF2E7D32)     // Primary green used in header and tags
val DashboardBackground = Color(0xFFF5F5F5) // Light gray background for the whole screen
val DashboardRed = Color(0xFFE53935)       // Red color for alerts/emergencies
val DashboardTextGray = Color(0xFF757575)   // Gray color for secondary text
val TagGreen = Color(0xFF2E7D32)            // Green color specifically for "School news" tags

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enables edge-to-edge display (drawing behind status/navigation bars)
        enableEdgeToEdge()
        setContent {
            // Apply the app's theme
            Parent_DashboardTheme {
                DashboardScreen()
            }
        }
    }
}

/**
 * MAIN SCREEN ASSEMBLY
 * This brings all the components together.
 */
@Composable
fun DashboardScreen() {
    // Scaffold provides the basic structure (TopBar, BottomBar, Content area)
    Scaffold(
        topBar = { DashboardHeader() },
        containerColor = DashboardBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding) // Respect the space taken by the TopBar
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Subtitle text at the top
            Text(
                text = "Welcome back! Here's what's happening\nwith your child's education.",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = DashboardTextGray,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            // LazyColumn is like a RecyclerView - it only renders visible items for performance
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp), // Space between cards
                contentPadding = PaddingValues(bottom = 16.dp)     // Space at the very bottom
            ) {
                // Attendance Card
                item {
                    DashboardCard(
                        title = "Overall Attendance",
                        value = "60%",
                        valueColor = DashboardRed,
                        barColor = TagGreen,
                        icon = Icons.Default.AssignmentTurnedIn,
                        tag = "School news",
                        tagColor = TagGreen,
                        date = "March 5, 2026"
                    )
                }
                // Payment Card
                item {
                    DashboardCard(
                        title = "Pending Payment",
                        value = "2,000",
                        valueColor = Color.Black,
                        barColor = DashboardRed,
                        icon = Icons.Default.Payments,
                        tag = "Emergency",
                        tagColor = DashboardRed,
                        date = "March 5, 2026"
                    )
                }
                // Children Enrolled Card
                item {
                    DashboardCard(
                        title = "Children Enrolled",
                        value = "2",
                        valueColor = Color.Black,
                        barColor = TagGreen,
                        icon = Icons.Default.ContactPage,
                        tag = "School news",
                        tagColor = TagGreen,
                        date = "March 5, 2026"
                    )
                }
                // Assignment/Info Card (Uses a different layout for longer text)
                item {
                    DashboardInfoCard(
                        title = "LIT -101",
                        subtitle = "Mr. Castro",
                        description = "Please review Chapter 5 for\ntomorrow's discussion.",
                        barColor = TagGreen,
                        icon = Icons.Default.Campaign,
                        tag = "School news",
                        tagColor = TagGreen
                    )
                }
            }
        }
    }
}

/**
 * HEADER COMPONENT
 * The green top bar with the title and icons.
 */
@Composable
fun DashboardHeader() {
    Surface(
        color = DashboardGreen,
        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp) // Rounded bottom corners
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding() // Ensures content doesn't go under the clock/battery icons
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = "Dashboard",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = RadioCanadaBig,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

/**
 * NUMERIC DATA CARD
 * Used for simple Stat displays (Attendance, Payments, etc.)
 */
@Composable
fun DashboardCard(
    title: String,
    value: String,
    valueColor: Color,
    barColor: Color,    // Color of the thin bar on the left
    icon: ImageVector,
    tag: String,
    tagColor: Color,
    date: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // LEFT ACCENT BAR: This creates the vertical colored stripe
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(6.dp)
                    .background(barColor)
            )
            
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = title, fontSize = 14.sp, color = DashboardTextGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        // The big number/percentage display
                        Text(
                            text = value,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = valueColor
                        )
                    }
                    // Large icon on the right side
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = DashboardGreen,
                        modifier = Modifier.size(40.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // BOTTOM ROW: Contains the tag, date, and arrow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // The colored badge/tag
                        Surface(
                            color = tagColor,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = tag,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = date, fontSize = 12.sp, color = Color.Black)
                    }
                    // Navigation arrow
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = DashboardTextGray
                    )
                }
            }
        }
    }
}

/**
 * INFORMATION CARD
 * Used for text-heavy updates like class info or messages.
 */
@Composable
fun DashboardInfoCard(
    title: String,
    subtitle: String,
    description: String,
    barColor: Color,
    icon: ImageVector,
    tag: String,
    tagColor: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // LEFT ACCENT BAR
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(6.dp)
                    .background(barColor)
            )
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = subtitle, fontSize = 14.sp, color = DashboardTextGray)
                        Spacer(modifier = Modifier.height(8.dp))
                        // Main text body
                        Text(
                            text = description,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DashboardTextGray
                        )
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = DashboardGreen,
                        modifier = Modifier.size(40.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // BOTTOM ROW: Tag and arrow only (no date here as per image)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        color = tagColor,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = tag,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = DashboardTextGray
                    )
                }
            }
        }
    }
}

/**
 * PREVIEW SECTION
 * This allows you to see the UI in Android Studio without running on a device.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardPreview() {
    Parent_DashboardTheme {
        DashboardScreen()
    }
}
