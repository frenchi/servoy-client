/*
 This file belongs to the Servoy development and deployment environment, Copyright (C) 1997-2022 Servoy BV

 This program is free software; you can redistribute it and/or modify it under
 the terms of the GNU Affero General Public License as published by the Free
 Software Foundation; either version 3 of the License, or (at your option) any
 later version.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License along
 with this program; if not, see http://www.gnu.org/licenses or write to the Free
 Software Foundation,Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
*/

package com.servoy.j2db.scripting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.javascript.annotations.JSGetter;

import com.servoy.j2db.documentation.ServoyDocumented;
import com.servoy.j2db.scripting.annotations.JSReadonlyProperty;
import com.servoy.j2db.util.Debug;

/**
 * This logger provides an API for logging with arguments, e.g. log.info().log("my message and my {}", "argument");.
 * This class can also be used to obtain JSLogBuilder instances.
 * Available logging levels are (in order): fatal, error, warn, info, debug and trace.
 *
 * @author jdejong
 *
 */
@ServoyDocumented(category = ServoyDocumented.RUNTIME, scriptingName = "JSLogger")
public class JSLogger
{
	private final Logger logger;


	/**
	 * Get the default logger
	 */
	public JSLogger()
	{
		this.logger = LogManager.getLogger(Debug.class);
	}

	/**
	 * Get a specific logger
	 *
	 * @param loggerName
	 */
	public JSLogger(String loggerName)
	{
		this.logger = LogManager.getLogger(loggerName);
	}

	// IS LEVEL ENABLED?
	/**
	 * Check if the current logger's logging level enables logging on the info level.
	 * Return true if the logger's level is set to info, debug or trace.
	 *
	 * @return true if 'info' level is enabled for logging
	 */
	@JSReadonlyProperty
	public boolean isInfoEnabled()
	{
		return logger.isInfoEnabled();
	}

	/**
	 * Check if the current logger's logging level enables logging on the warn level.
	 * Return true if the logger's level is set to warn, info, debug or trace.
	 *
	 * @return true if 'warn' level is enabled for logging
	 */
	@JSReadonlyProperty
	public boolean isWarnEnabled()
	{
		return logger.isWarnEnabled();
	}

	/**
	 * Check if the current logger's logging level enables logging on the error level.
	 * Return true if the logger's level is set to error, warn, info, debug or trace.
	 *
	 * @return true if 'error' level is enabled for logging
	 */
	@JSReadonlyProperty
	public boolean isErrorEnabled()
	{
		return logger.isErrorEnabled();
	}

	/**
	 * Check if the current logger's logging level enables logging on the debug level.
	 * Return true if the logger's level is set to debug or trace.
	 *
	 * @return true if 'debug' level is enabled for logging
	 */
	@JSReadonlyProperty
	public boolean isDebugEnabled()
	{
		return logger.isDebugEnabled();
	}

	/**
	 * Check if the current logger's logging level enables logging on the trace level.
	 * Return true if the logger's level is set to trace.
	 *
	 * @return true if 'trace' level is enabled for logging
	 */
	@JSReadonlyProperty
	public boolean isTraceEnabled()
	{
		return logger.isTraceEnabled();
	}

	/**
	 * Check if the current logger's logging level enables logging on the fatal level.
	 * Return true if the logger's level is set to fatal, error, warn, info, debug or trace.
	 *
	 * @return true if 'fatal' level is enabled for logging
	 */
	@JSReadonlyProperty
	public boolean isFatalEnabled()
	{
		return logger.isFatalEnabled();
	}

	// BUILDER
	/**
	 * Construct an info log event.
	 *
	 * @sample
	 * var log = application.getLogger();
	 * log.info().log("some message and {} {}", "some", "arguments");
	 *
	 * @return a LogBuilder.
	 */
	@JSGetter
	public JSLogBuilder info()
	{
		return new JSLogBuilder(logger.atInfo());
	}

	/**
	 * Construct a warn log event.
	 *
	 * @sample
	 * var log = application.getLogger();
	 * log.warn().log("some message and {} {}", "some", "arguments");
	 *
	 * @return a LogBuilder.
	 */
	@JSGetter
	public JSLogBuilder warn()
	{
		return new JSLogBuilder(logger.atWarn());
	}

	/**
	 * Construct a debug log event.
	 *
	 * @sample
	 * var log = application.getLogger();
	 * log.debug().log("some message and {} {}", "some", "arguments");
	 *
	 * @return a LogBuilder.
	 */
	@JSGetter
	public JSLogBuilder debug()
	{
		return new JSLogBuilder(logger.atDebug());
	}

	/**
	 * Construct an error log event.
	 *
	 * @sample
	 * var log = application.getLogger();
	 * log.error().log("some message and {} {}", "some", "arguments");
	 *
	 * @return a LogBuilder.
	 */
	@JSGetter
	public JSLogBuilder error()
	{
		return new JSLogBuilder(logger.atError());
	}

	/**
	 * Construct a trace log event.
	 *
	 * @sample
	 * var log = application.getLogger();
	 * log.trace().log("some message and {} {}", "some", "arguments");
	 *
	 * @return a LogBuilder.
	 */
	@JSGetter
	public JSLogBuilder trace()
	{
		return new JSLogBuilder(logger.atTrace());
	}

	/**
	 * Construct a log event that will always be logged.
	 *
	 * @sample
	 * var log = application.getLogger();
	 * log.always().log("some message and {} {}", "some", "arguments");
	 *
	 * @return a LogBuilder.
	 */
	@JSGetter
	public JSLogBuilder always()
	{
		return new JSLogBuilder(logger.always());
	}

}
