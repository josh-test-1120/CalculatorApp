package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import com.example.calculatorapp.ui.theme.AllClearButton
import com.example.calculatorapp.ui.theme.ButtonText
import com.example.calculatorapp.ui.theme.CalculatorAppTheme
import com.example.calculatorapp.ui.theme.ClearButton
import com.example.calculatorapp.ui.theme.GoldButton
import com.example.calculatorapp.ui.theme.NumberBackground
import com.example.calculatorapp.ui.theme.NumberButton
import org.mozilla.javascript.Context
import org.mozilla.javascript.ContextFactory
import org.mozilla.javascript.Scriptable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator("standard")
                }
            }
        }
    }
}

@Composable
fun Calculator(style: String, modifier: Modifier = Modifier) {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (viewBox, buttonBox) = createRefs()
        // Author details
        var author = "Summers"
        var cwuid ="49933065"
        // Create the state for mutable text fields
        var viewText by rememberSaveable { mutableStateOf(cwuid) }
        var sumText by rememberSaveable { mutableStateOf(author) }
        // This is the view box
        Column(
            modifier = Modifier
                .constrainAs(viewBox) {
                    bottom.linkTo(buttonBox.top, margin = 16.dp)
                }
        )
        {
            // This is the view box
            ViewBox(viewText);
            // This is the summation box
            SummationBox(sumText);
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the viewBox composable
        Column(
            modifier = Modifier
                .constrainAs(buttonBox) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
                .padding(16.dp)
                .fillMaxWidth()
                .background(NumberBackground, RectangleShape),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // First row of buttons
            ButtonsRow1(viewText,cwuid,viewTextOnChange={ newValue -> viewText = newValue });
            // Second row of buttons
            ButtonsRow2(viewText,cwuid,viewTextOnChange={ newValue -> viewText = newValue });
            // Third row of buttons
            ButtonsRow3(viewText,cwuid,viewTextOnChange={ newValue -> viewText = newValue });
            // Fourth row of buttons
            ButtonsRow4(viewText,cwuid,viewTextOnChange={ newValue -> viewText = newValue });
            // Fifth row of buttons
            ButtonsRow5(viewText,sumText,cwuid,
                viewTextOnChange={ newValue -> viewText = newValue },
                sumTextOnChange={ newValue -> sumText = newValue });
        }
    }
}

@Composable
fun ViewBox(values: String) {
    Text(
        values,
        textAlign = TextAlign.Right,
        fontSize = 42.sp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun SummationBox(values: String) {
    Text(
        values,
        textAlign = TextAlign.Right,
        fontSize = 56.sp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ButtonsRow1(views: String, author: String, viewTextOnChange: ((String) -> Unit) = {}) {
    // First row of buttons
    Row() {
        FloatingActionButton(
            onClick = { viewTextOnChange("${views.subSequence(0,views.length-1)}");},
            shape = RoundedCornerShape(50),

            modifier = Modifier.padding(16.dp),
            containerColor = ClearButton,
            contentColor = ButtonText)
        {
            Text(
                "C",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"(");
                else viewTextOnChange("(");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "(",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+")");
                else viewTextOnChange(")");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                ")",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"/");
                else viewTextOnChange("/");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = GoldButton,
            contentColor = ButtonText)
        {
            Text(
                "/",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Composable
fun ButtonsRow2(views: String, author: String, viewTextOnChange: ((String) -> Unit) = {}) {
    Row() {
        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"7");
                else viewTextOnChange("7");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "7",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"8");
                else viewTextOnChange("8");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "8",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"9");
                else viewTextOnChange("9");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "9",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"*");
                else viewTextOnChange("*");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = GoldButton,
            contentColor = ButtonText)
        {
            Text(
                "*",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Composable
fun ButtonsRow3(views: String, author: String, viewTextOnChange: ((String) -> Unit) = {}) {
    Row() {
        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"4");
                else viewTextOnChange("4");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "4",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"5");
                else viewTextOnChange("5");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "5",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"6");
                else viewTextOnChange("6");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "6",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"+");
                else viewTextOnChange("+");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = GoldButton,
            contentColor = ButtonText)
        {
            Text(
                "+",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Composable
fun ButtonsRow4(views: String, author: String, viewTextOnChange: ((String) -> Unit) = {}) {
    Row() {
        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"1");
                else viewTextOnChange("1");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "1",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"2");
                else viewTextOnChange("2");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "2",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"3");
                else viewTextOnChange("3");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                "3",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+"-");
                else viewTextOnChange("-");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = GoldButton,
            contentColor = ButtonText)
        {
            Text(
                "-",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Composable
fun ButtonsRow5(views: String, sums: String, author: String,
                viewTextOnChange: ((String) -> Unit) = {},
                sumTextOnChange: ((String) -> Unit) = {})
{
    Row() {
        // This is the AC button
        FloatingActionButton(
            onClick = {
                viewTextOnChange("0");
                sumTextOnChange("0");
                      },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 56.dp)
                .heightIn(min = 66.dp),
            containerColor = AllClearButton,
            contentColor = ButtonText)
        {
            Text(
                "AC",
                fontSize = 20.sp,
                modifier = Modifier.padding(14.dp)
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") { viewTextOnChange("$views"+"0"); }
                      },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = NumberButton,
            contentColor = ButtonText)
        {
            Text(
                "0",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }

        FloatingActionButton(
            onClick = {
                if (views != author && views != "0") viewTextOnChange("$views"+".");
                else viewTextOnChange(".");
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = Color.DarkGray,
            contentColor = ButtonText)
        {
            Text(
                ".",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }
        // This is the equals or evaluate button
        FloatingActionButton(
            onClick = {
                try {
                    // Setup the context for the evaluation
                    var context = Context.enter();
                    context.optimizationLevel = -1;
                    var scriptable = context.initStandardObjects();
                    var result = context.evaluateString(scriptable,views,"Javascript",
                        1,null).toString();
                    System.out.println("This is the result: $result");
                    sumTextOnChange(result);
                }
                // Does nothing except log the error
                catch (e: Exception) {
                    System.out.println("Error: $e");
                }
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(16.dp),
            containerColor = GoldButton,
            contentColor = ButtonText)
        {
            Text(
                "=",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

// Main Preview function, which is just a wrapper for the main function
@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorAppTheme {
        Calculator("standard")
    }
}