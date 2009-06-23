package hu.cubussapiens.debugvisualisation;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	/**
	 * The plug-in ID
	 */
	public static final String PLUGIN_ID = "hu.cubussapiens.debugvisualisation";

	// The shared instance
	private static Activator plugin;

	private ILog log;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Bundle bundle = context.getBundle();
		new ImagePool(bundle);
		plugin = this;
		log = Platform.getLog(bundle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		ImagePool.getInstance().dispose();
		super.stop(context);
		log = null;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns the logger interface of the bundle
	 * 
	 * @return the logger interface
	 */
	public ILog getLogger() {
		return log;
	}

	/**
	 * Logs an error message together with the an exception
	 * 
	 * @param e
	 *            an Exception to log
	 * @param message
	 *            the log message
	 */
	public void logError(Throwable e, String message) {
		logError(e, message, -1);
	}

	/**
	 * Logs an error message with an error code and an exception
	 * 
	 * @param e
	 *            an Exception to log
	 * @param message
	 *            the log message
	 * @param errorCode
	 *            an error code
	 */
	public void logError(Throwable e, String message, int errorCode) {
		Status status = new Status(IStatus.ERROR, PLUGIN_ID, errorCode,
				message, e);
		log.log(status);
	}

}
