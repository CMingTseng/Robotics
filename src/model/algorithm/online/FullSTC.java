package model.algorithm.online;

import java.awt.Point;
import java.util.Vector;

import model.algorithm.Algorithm;
import model.object.Edge;
import model.object.MegaCell;
import model.object.robot.Robot;
import utils.Config;

/**
 * @author Thien Nguyen created by Mar 29, 2016 
 *
 */
public class FullSTC extends Algorithm{

	public FullSTC(MegaCell[][] map, Robot robot, Point start, Vector<Edge> edge, Config mConfig) {
		super(map, robot, start, edge, mConfig);
	}

	@Override
	public void run() {
	}

	@Override
	protected void initData(MegaCell[][] arr) {
	}

}
