
#  prototype for p2dx sonar sensors
define robot_sonar sensor
(
		size [0.01 0.05 0.01 ]
		range [0.2 4.0]
		fov 15
		samples 1
		color_rgba [ 0 1 0 0.2 ]
)
define p2dxlaser sensor
(
		size [0.01 0.05 0.01 ]
		range_max 5.0
		fov 360
		samples 180
		color_rgba [ 0 1 0 0.2 ]
)
define p2dx_sonar ranger
(
		robot_sonar( pose [ 0 0.5 0 90 ])
		robot_sonar( pose [ 0.25 0.5 0 67.5 ])
		robot_sonar( pose [ 0.375 0.375 0 45 ])
		robot_sonar( pose [ 0.5 0.25 0 22.5 ])
		robot_sonar( pose [ 0.5 0 0 0 ])
		robot_sonar( pose [ 0.5 -0.25 0 -22.5 ])
		robot_sonar( pose [ 0.375 -0.375 0 -45 ])
		robot_sonar( pose [ 0.25 -0.5 0 -67.5 ])
		robot_sonar( pose [ 0 -0.5 0 -90 ])
)

define pioneer2dx position
(
	color "red"
	size [1 1 0.3]
	origin [0 0 0 0]
	gui_nose 1
	mass 23.0
		block(
				points 8
				point[0] [0.25 0.5]
				point[1] [0.5 0.25]
				point[2] [0.5 -0.25]
				point[3] [0.25 -0.5]
				point[4] [-0.25 -0.5]
				point[5] [-0.5 -0.25]
				point[6] [-0.5 0.25]
				point[7] [-0.25 0.5]
				z [0 0.3]
		)

	drive "diff"
	p2dx_sonar()
)
