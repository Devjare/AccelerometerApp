# Accelerometer application.

### Accelerometer Frequency.

Accelerometer takes samples considering various factors: 
- Delay specified(UI, GAME, FASTEST, NORMAL) constanst given by android Studio(Managable)
- OS Interruptions(Non manageble)

Different scenarios results:

| DelayConstant | Samples Per Second	|
|---------------|------------------------
|	UI	|	52 - 53		|
|      GAME	|	50 - 55		|
|     NORMAL	|	50 - 57		|
|     FASTEST	|      210 - 216	|

When interrupted, sometimes more or less samples were taken.
Samples per second are observable results by using a simple counter and adding 1 to it
each time sensor data is received, at 1000 milliseconds(1 secons) the data is shown.  
While counter may be incrementing, the accelerometer data on that moment is not displayed nor manipulated.
Only each second the gravity extraction happens, it is then when the counter restarts to 0, to again
start counting samples per second.

In general, an average of 53 with no interruptions was observed on UI, GAME and NORMAL.
On FASTEST, an average of 212 was observed.

