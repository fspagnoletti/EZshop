# Project Dashboard

The dashboard collects the key measures about the project.
To be used to control the project, and compare with others. These measures will not be used to grade the project. <br>
We consider two phases: <br>
-New development: From project start (april 5) to delivery of version 0 (release 0, may 28) <br>
-Corrective Maintenance: fix of defects (if any)  (may 28 to june 4)   <br>
Report effort figures from the timesheet or timesheetCR document, compute size from the source code.

## New development (release 0  -- april 5 to may 28)
| Measure| Value |
|---|---|
|effort E (Report in TimeSheet the effort (in person hours) spent per week, per activity, per team. [One person hour is the effort of one person working one hour)  |560|
|size S (report here size in LOC of all code written, excluding test cases)  |3934|
|productivity = S/E |7.025|
|defects before release D_before (number of defects found and fixed before may 28) |48|




## Corrective Maintenance (release 1 -- may 28 to june 4)

| Measure | Value|
|---|---|
| effort for non-quality ENQ (effort for release 1, or effort to fix defects found when running official acceptance tests) |.8|
| effort for non quality, relative = ENQ / E |0.001|
|defects after release D (number of defects found running official acceptance tests and  fixed in release 1) |1|
| defects before release vs defects after release = D/D_before |0,0208|
|defect density = D/S|2,54e-4|
|overall productivity = S/(E + ENQ)|7,025|
