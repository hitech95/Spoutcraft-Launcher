/*
 * This file is part of LauncherAPI (http://www.spout.org/).
 *
 * LauncherAPI is licensed under the SpoutDev License Version 1.
 *
 * LauncherAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * LauncherAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */

package org.spoutcraft.launcher.api.skin.gui;

import java.util.List;

import org.jdesktop.swingworker.SwingWorker;
import org.spoutcraft.launcher.api.Event;
import org.spoutcraft.launcher.api.Launcher;

public class CheckUpdatesWorker extends SwingWorker<Boolean, String> {
	
	private final LoginFrame loginFrame;
	private boolean mcUpdate = false, scUpdate = false;
	public CheckUpdatesWorker(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}
	
	protected Boolean doInBackground() throws Exception {
		publish("Checking for Minecraft Update...\n");
		try {
			mcUpdate = Launcher.getGameUpdater().isMinecraftUpdateAvailible();
		} catch (Exception e) {
			mcUpdate = false;
		}

		publish("Checking for Spoutcraft update...\n");
		try {
			scUpdate = mcUpdate || Launcher.getGameUpdater().isSpoutcraftUpdateAvailible();
		} catch (Exception e) {
			scUpdate = false;
		}
		return true;
	}

	protected void done() {		
		loginFrame.setSpoutcraftUpdateAvailable(scUpdate);
		loginFrame.setMinecraftUpdateAvailable(mcUpdate);
		loginFrame.onRawEvent(Event.FINISHED_UPDATE_CHECK);
		this.cancel(true);
	}

	protected void process(List<String> chunks) {
		loginFrame.getProgressBar().setString(chunks.get(0));
	}

}
