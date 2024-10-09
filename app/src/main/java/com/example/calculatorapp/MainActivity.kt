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

/**
 * This is the main activity class that drives the UI
 * extends the ComponentActivity
 */
class MainActivity : ComponentActivity() {
    /**
     * Override the onCreate function
     * @param savedInstanceState This is an instance of Bundle
     * that can be empty
     */
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

/**
 * This is the Main Calculator composable
 * @param style This is the style of calculator to present
 * @param modifier This is a Modifier object to pass along
 */
@Composable
fun Calculator(style: String, modifier: Modifier = Modifier) {
    // Layout Constraint for placement handling
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
            // Constraint to top of button box
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

        // This is the calculator buttons box
        Column(
            // Constraint to bottom of the parent
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

/**
 * This is the View Box composable
 * @param values This is the value string to render
 */
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

/**
 * This is the Summation Box composable
 * @param values This is the value string to render
 */
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

/**
 * This is the Row 1 Buttons composable
 * @param viewText This is the current value of the view Text
 * @param author This is the current value of the author
 * @param viewTextOnChange This is the callback function that updates view Text
 * @param viewTextOnChange This is the callback function that updates view Text
 */
@Composable
fun ButtonsRow1(viewText: String, author: String, viewTextOnChange: ((String) -> Unit) = {}) {
    // First row of buttons
    Row() {
        FloatingActionButton(
            // Updates the viewText with a substring of one less of length
            onClick = { viewTextOnChange("${viewText.subSequence(0,viewText.length-1)}");},
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"(");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+")");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"/");
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

/**
 * This is the Row 2 Buttons composable
 * @param viewText This is the current value of the view Text
 * @param author This is the current value of the author
 * @param viewTextOnChange This is the callback function that updates view Text
 */
@Composable
fun ButtonsRow2(viewText: String, author: String, viewTextOnChange: ((String) -> Unit) = {}) {
    Row() {
        FloatingActionButton(
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"7");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"8");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"9");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"*");
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

/**
 * This is the Row 3 Buttons composable
 * @param viewText This is the current value of the view Text
 * @param author This is the current value of the author
 * @param viewTextOnChange This is the callback function that updates view Text
 */
@Composable
fun ButtonsRow3(viewText: String, author: String, viewTextOnChange: ((String) -> Unit) = {}) {
    Row() {
        FloatingActionButton(
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"4");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"5");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"6");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"+");
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

/**
 * This is the Row 4 Buttons composable
 * @param viewText This is the current value of the view Text
 * @param author This is the current value of the author
 * @param viewTextOnChange This is the callback function that updates view Text
 */
@Composable
fun ButtonsRow4(viewText: String, author: String, viewTextOnChange: ((String) -> Unit) = {}) {
    Row() {
        FloatingActionButton(
            onClick = {
                // Updates the viewText depending on initial values; otherwise it appends
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"1");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"2");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"3");
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+"-");
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

/**
 * This is the Row 5 Buttons composable
 * @param viewText This is the current value of the view Text
 * @param author This is the current value of the author
 * @param viewTextOnChange This is the callback function that updates view Text
 * @param sumTextOnChange This is the callback function that updates sum Text
 */
@Composable
fun ButtonsRow5(viewText: String, sums: String, author: String,
                viewTextOnChange: ((String) -> Unit) = {},
                sumTextOnChange: ((String) -> Unit) = {})
{
    Row() {
        // This is the AC button
        FloatingActionButton(
            // Updates the viewText and sumText to be 0
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") { viewTextOnChange("$viewText"+"0"); }
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
            // Updates the viewText depending on initial values; otherwise it appends
            onClick = {
                if (viewText != author && viewText != "0") viewTextOnChange("$viewText"+".");
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
            // Evaluates the viewText and renders it in the sumText
            onClick = {
                try {
                    // Setup the context for the evaluation
                    var context = Context.enter();
                    context.optimizationLevel = -1;
                    var scriptable = context.initStandardObjects();
                    var result = context.evaluateString(scriptable,viewText,"Javascript",
                        1,null).toString();
                    // Truncates the result to 12 digits to fit the Summation Box
                    result = truncateString(result,12)
                    System.out.println("This is the result: $result")
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

/**
 * This is the truncate string function
 * This is needed to ensure the view box does not overflow
 * @param text This is string to truncate
 * @param length This is length to truncate string to
 * @return substring string of the original text
 */
fun truncateString(text: String, length: Int): String {
    if (text.length > length) return text.substring(0,length)
    else return text
}

/**
 * This is the Main preview function composable
 * This is what loads when live-preview is enabled
 * @param showBackground This is set to true for default background color
 */
@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorAppTheme {
        // Only the default style is implemented currently
        Calculator("standard")
    }
}