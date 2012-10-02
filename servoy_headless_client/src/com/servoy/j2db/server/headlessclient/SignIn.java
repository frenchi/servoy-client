/*
 This file belongs to the Servoy development and deployment environment, Copyright (C) 1997-2010 Servoy BV

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
package com.servoy.j2db.server.headlessclient;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.persistence.CookieValuePersister;
import org.apache.wicket.markup.html.form.persistence.CookieValuePersisterSettings;
import org.apache.wicket.markup.html.form.persistence.IValuePersister;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.util.value.ValueMap;

import com.servoy.j2db.util.Settings;

public class SignIn extends WebPage
{
	/** True if the panel should display a remember-me checkbox */
	private static final boolean includeRememberMe = true;

	/** Field for password. */
	private PasswordTextField password;

	/** True if the user should be remembered via form persistence (cookies) */
	private boolean rememberMe = true;

	/** Field for user name. */
	private TextField username;

	/**
	 * @param id See Component constructor
	 * @param includeRememberMe True if form should include a remember-me checkbox
	 * @see wicket.Component#Component(String)
	 */
	public SignIn()
	{
		// Create feedback panel and add to page
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);

		// Add sign-in form to page, passing feedback panel as
		// validation error handler
		add(new SignInForm("signInForm"));
	}

	/**
	 * Sign in form.
	 */
	public final class SignInForm extends Form
	{
		/** El-cheapo model for form. */
		private final ValueMap properties = new ValueMap();

		/**
		 * Constructor.
		 * 
		 * @param id id of the form component
		 * @param feedback The feedback panel to update
		 */
		public SignInForm(final String id)
		{
			super(id);

			// Attach textfield components that edit properties map
			// in lieu of a formal beans model
			add(username = new TextField("username", new PropertyModel(properties, "username"), String.class));
			add(password = new PasswordTextField("password", new PropertyModel(properties, "password"))
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected boolean supportsPersistence()
				{
					return true;
				}
			});
			password.setType(String.class);

			// MarkupContainer row for remember me checkbox
			WebMarkupContainer rememberMeRow = new WebMarkupContainer("rememberMeRow");
			add(rememberMeRow);

			// Add rememberMe checkbox
			rememberMeRow.add(new CheckBox("rememberMe", new PropertyModel(SignIn.this, "rememberMe")));

			// Make form values persistent
			setPersistent(rememberMe);

			// Show remember me checkbox?
			rememberMeRow.setVisible(includeRememberMe);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.apache.wicket.markup.html.form.Form#onBeforeRender()
		 */
		@Override
		protected void onBeforeRender()
		{
			super.onBeforeRender();

			String usr = username.getDefaultModelObjectAsString();
			String pwd = password.getDefaultModelObjectAsString();
			if (usr != "" && usr != null && pwd != "" && pwd != null)
			{
				if (signIn(usr, pwd))
				{
					if (!getPage().continueToOriginalDestination())
					{
						setResponsePage(Session.get().getPageFactory().newPage(getApplication().getHomePage(), null));
					}
				}
			}
		}

		/**
		 * @see wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public final void onSubmit()
		{
			if (signIn(username.getDefaultModelObjectAsString(), password.getDefaultModelObjectAsString()))
			{
				// If login has been called because the user was not yet
				// logged in, then continue to the original destination,
				// otherwise to the Home page
				if (!getPage().continueToOriginalDestination())
				{
					setResponsePage(Session.get().getPageFactory().newPage(getApplication().getHomePage(), null));
				}
			}
			else
			{
				// Try the component based localizer first. If not found try the
				// application localizer. Else use the default
				final String errmsg = getLocalizer().getString("loginError", this, "Unable to sign you in"); //$NON-NLS-1$ //$NON-NLS-2$
				error(errmsg);
			}
		}

		@Override
		protected IValuePersister getValuePersister()
		{
			// use secure cookies for persistent fields
			CookieValuePersisterSettings settings = new CookieValuePersisterSettings();
			settings.setSecure(Boolean.parseBoolean(Settings.getInstance().getProperty("servoy.webclient.enforceSecureCookies", "false")) ||
				((WebRequestCycle)RequestCycle.get()).getWebRequest().getHttpServletRequest().isSecure());

			return new CookieValuePersister(settings);
		}

	}

	/**
	 * Convenience method set persistence for username and password.
	 * 
	 * @param enable Whether the fields should be persistent
	 */
	private void setPersistent(boolean enable)
	{
		username.setPersistent(enable);
		password.setPersistent(enable);

		if (!enable)
		{
			// Remove persisted user data. Search for child component
			// of type SignInForm and remove its related persistence values.
			getPage().removePersistedFormData(SignInForm.class, true);
		}
	}

	/**
	 * Get model object of the rememberMe checkbox
	 * 
	 * @return True if user should be remembered in the future
	 */
	public boolean getRememberMe()
	{
		return rememberMe;
	}

	/**
	 * Set model object for rememberMe checkbox
	 */
	public void setRememberMe(boolean rememberMe)
	{
		this.rememberMe = rememberMe;
		this.setPersistent(rememberMe);
	}

	/**
	 * Sign in user if possible.
	 * 
	 * @param username The username
	 * @param password The password
	 * @return True if signin was successful
	 */
	private boolean signIn(final String username, final String password)
	{
		return ((WebClientSession)getSession()).authenticate(username, password);
	}
}
