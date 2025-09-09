package com.expenseecho.ui.components.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expenseecho.data.analytics.*
import com.expenseecho.util.formatCurrency
import kotlin.math.*

/**
 * Pie chart component for category spending
 */
@Composable
fun PieChart(
    data: List<CategoryAnalysis>,
    modifier: Modifier = Modifier,
    showLegend: Boolean = true
) {
    val totalAmount = data.sumOf { it.amount }
    if (totalAmount == 0L) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("No data available", style = MaterialTheme.typography.bodyMedium)
        }
        return
    }
    
    val colors = listOf(
        Color(0xFFE57373), Color(0xFF64B5F6), Color(0xFF81C784), Color(0xFFFFB74D),
        Color(0xFFF06292), Color(0xFF4DB6AC), Color(0xFFFF8A65), Color(0xFF9575CD)
    )
    
    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = minOf(size.width, size.height) / 2f * 0.8f
            var startAngle = -90f
            
            data.forEachIndexed { index, item ->
                val sweepAngle = (item.amount.toFloat() / totalAmount.toFloat()) * 360f
                val color = colors[index % colors.size]
                
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2)
                )
                
                startAngle += sweepAngle
            }
        }
        
        if (showLegend) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                data.take(8).forEach { item ->
                    LegendItem(
                        color = colors[data.indexOf(item) % colors.size],
                        label = item.category.name,
                        value = item.amount,
                        percentage = item.percentage
                    )
                }
            }
        }
    }
}

/**
 * Bar chart component for trend data
 */
@Composable
fun BarChart(
    data: List<TrendData>,
    modifier: Modifier = Modifier,
    showValues: Boolean = true
) {
    if (data.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("No data available", style = MaterialTheme.typography.bodyMedium)
        }
        return
    }
    
    val maxValue = data.maxOf { maxOf(it.income, it.expenses) }
    
    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val barWidth = size.width / (data.size * 2.5f)
            val maxHeight = size.height * 0.8f
            
            data.forEachIndexed { index, item ->
                val x = (index * size.width / data.size) + (size.width / data.size - barWidth) / 2
                val incomeHeight = (item.income.toFloat() / maxValue.toFloat()) * maxHeight
                val expenseHeight = (item.expenses.toFloat() / maxValue.toFloat()) * maxHeight
                
                // Income bar
                drawRect(
                    color = Color(0xFF4CAF50),
                    topLeft = Offset(x, size.height - incomeHeight),
                    size = Size(barWidth * 0.4f, incomeHeight)
                )
                
                // Expense bar
                drawRect(
                    color = Color(0xFFF44336),
                    topLeft = Offset(x + barWidth * 0.5f, size.height - expenseHeight),
                    size = Size(barWidth * 0.4f, expenseHeight)
                )
            }
        }
        
        if (showValues) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                data.forEach { item ->
                    Text(
                        text = item.period,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

/**
 * Line chart component for trends
 */
@Composable
fun LineChart(
    data: List<TrendData>,
    modifier: Modifier = Modifier,
    showDots: Boolean = true
) {
    if (data.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("No data available", style = MaterialTheme.typography.bodyMedium)
        }
        return
    }
    
    val maxValue = data.maxOf { maxOf(it.income, it.expenses) }
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val stepX = size.width / (data.size - 1).coerceAtLeast(1)
        val maxHeight = size.height * 0.8f
        
        // Draw income line
        if (data.size > 1) {
            val incomePoints = data.mapIndexed { index, item ->
                Offset(
                    index * stepX,
                    size.height - (item.income.toFloat() / maxValue.toFloat()) * maxHeight
                )
            }
            
            drawLine(
                color = Color(0xFF4CAF50),
                start = incomePoints.first(),
                end = incomePoints.last(),
                strokeWidth = 4.dp.toPx()
            )
            
            // Draw expense line
            val expensePoints = data.mapIndexed { index, item ->
                Offset(
                    index * stepX,
                    size.height - (item.expenses.toFloat() / maxValue.toFloat()) * maxHeight
                )
            }
            
            drawLine(
                color = Color(0xFFF44336),
                start = expensePoints.first(),
                end = expensePoints.last(),
                strokeWidth = 4.dp.toPx()
            )
            
            // Draw dots
            if (showDots) {
                incomePoints.forEach { point ->
                    drawCircle(
                        color = Color(0xFF4CAF50),
                        radius = 6.dp.toPx(),
                        center = point
                    )
                }
                
                expensePoints.forEach { point ->
                    drawCircle(
                        color = Color(0xFFF44336),
                        radius = 6.dp.toPx(),
                        center = point
                    )
                }
            }
        }
    }
}

/**
 * Horizontal bar chart for merchant analysis
 */
@Composable
fun HorizontalBarChart(
    data: List<MerchantAnalysis>,
    modifier: Modifier = Modifier,
    maxItems: Int = 10
) {
    if (data.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("No data available", style = MaterialTheme.typography.bodyMedium)
        }
        return
    }
    
    val maxAmount = data.maxOf { it.totalAmount }
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        data.take(maxItems).forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.merchantName,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .height(20.dp)
                        .clip(MaterialTheme.shapes.small)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val barWidth = (item.totalAmount.toFloat() / maxAmount.toFloat()) * size.width
                        drawRect(
                            color = Color(0xFF2196F3),
                            size = Size(barWidth, size.height)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = formatCurrency(item.totalAmount),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Legend item for charts
 */
@Composable
private fun LegendItem(
    color: Color,
    label: String,
    value: Long,
    percentage: Float
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(MaterialTheme.shapes.small)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(color = color)
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = "${percentage.toInt()}%",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.width(4.dp))
        
        Text(
            text = formatCurrency(value),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Donut chart component
 */
@Composable
fun DonutChart(
    data: List<CategoryAnalysis>,
    modifier: Modifier = Modifier,
    centerText: String = ""
) {
    val totalAmount = data.sumOf { it.amount }
    if (totalAmount == 0L) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("No data available", style = MaterialTheme.typography.bodyMedium)
        }
        return
    }
    
    val colors = listOf(
        Color(0xFFE57373), Color(0xFF64B5F6), Color(0xFF81C784), Color(0xFFFFB74D),
        Color(0xFFF06292), Color(0xFF4DB6AC), Color(0xFFFF8A65), Color(0xFF9575CD)
    )
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val outerRadius = minOf(size.width, size.height) / 2f * 0.8f
            val innerRadius = outerRadius * 0.6f
            var startAngle = -90f
            
            data.forEachIndexed { index, item ->
                val sweepAngle = (item.amount.toFloat() / totalAmount.toFloat()) * 360f
                val color = colors[index % colors.size]
                
                // Draw outer arc
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(center.x - outerRadius, center.y - outerRadius),
                    size = Size(outerRadius * 2, outerRadius * 2),
                    style = Stroke(width = (outerRadius - innerRadius))
                )
                
                startAngle += sweepAngle
            }
        }
        
        if (centerText.isNotEmpty()) {
            Text(
                text = centerText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
