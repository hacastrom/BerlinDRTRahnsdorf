
/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2016 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

// ADAPTED FROM  jfbischoff/drt-melbourne

package main.drt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.dvrp.data.Vehicle;
import org.matsim.contrib.dvrp.data.VehicleImpl;
import org.matsim.contrib.dvrp.data.file.VehicleWriter;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.gbl.MatsimRandom;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;

/**
 * @author jbischoff This is an example script to create (robo)taxi vehicle
 *         files. The vehicles are distributed randomly in the network.
 *
 */
public class CreateDrtVehicles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
		double operationStartTime = 0; // t0
		double operationEndTime = 36 * 3600.; // t1
		int seats = 8;
		int numberofVehicles = 10;

		String networkfile = "C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA2\\network_berlin.xml";
		String taxisFile = ".\\scenarios\\berlin\\input\\taxis_10.xml";
		List<Vehicle> vehicles = new ArrayList<>();

		Random random = MatsimRandom.getLocalInstance();
		new MatsimNetworkReader(scenario.getNetwork()).readFile(networkfile);
		// near Lilydale station
		Link startLink = scenario.getNetwork().getLinks().get(Id.createLinkId(18821)); // Should be in Frohnau

		for (int i = 0; i < numberofVehicles; i++) {

			while (!startLink.getAllowedModes().contains(TransportMode.car))
				;
			// for multi-modal networks: Only links where cars can ride should
			// be used.
			Vehicle v = new VehicleImpl(Id.create("taxi" + i, Vehicle.class), startLink, seats, operationStartTime,
					operationEndTime);
			vehicles.add(v);

		}
		new VehicleWriter(vehicles).write(taxisFile);
	}

}
