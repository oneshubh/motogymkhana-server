package eu.motogymkhana.server.guice;

import org.apache.http.client.HttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;

import eu.motogymkhana.server.dao.PasswordDao;
import eu.motogymkhana.server.dao.RiderAuthDao;
import eu.motogymkhana.server.dao.RiderDao;
import eu.motogymkhana.server.dao.RoundDao;
import eu.motogymkhana.server.dao.SettingsDao;
import eu.motogymkhana.server.dao.TimesDao;
import eu.motogymkhana.server.dao.impl.PasswordDaoImpl;
import eu.motogymkhana.server.dao.impl.RiderAuthDaoImpl;
import eu.motogymkhana.server.dao.impl.RiderDaoImpl;
import eu.motogymkhana.server.dao.impl.RoundDaoImpl;
import eu.motogymkhana.server.dao.impl.SettingsDaoImpl;
import eu.motogymkhana.server.dao.impl.TimesDaoImpl;
import eu.motogymkhana.server.http.HttpClientProvider;
import eu.motogymkhana.server.http.MyHttpClient;
import eu.motogymkhana.server.http.UrlHelper;
import eu.motogymkhana.server.http.impl.MyHttpClientImpl;
import eu.motogymkhana.server.http.impl.UrlHelperImpl;
import eu.motogymkhana.server.jackson.ObjectMapperProvider;
import eu.motogymkhana.server.jpa.EntityManagerHelper;
import eu.motogymkhana.server.jpa.impl.EntityManagerHelperImpl;
import eu.motogymkhana.server.password.PasswordManager;
import eu.motogymkhana.server.password.impl.PasswordManagerImpl;
import eu.motogymkhana.server.round.RoundManager;
import eu.motogymkhana.server.round.impl.RoundManagerImpl;
import eu.motogymkhana.server.text.TextManager;
import eu.motogymkhana.server.text.impl.TextManagerImpl;
import eu.motogymkhana.server.timer.TimerManager;
import eu.motogymkhana.server.timer.impl.TimerManagerImpl;

/**
 * 
 * @author christine
 * 
 */
public class GymkhanaModule extends AbstractModule {

	@Override
	protected void configure() {
		
		bind(EntityManagerHelper.class).to(EntityManagerHelperImpl.class);
		
		bind(RoundDao.class).to(RoundDaoImpl.class);
		bind(TimesDao.class).to(TimesDaoImpl.class);
		bind(RiderDao.class).to(RiderDaoImpl.class);
		bind(PasswordDao.class).to(PasswordDaoImpl.class);
		bind(SettingsDao.class).to(SettingsDaoImpl.class);
		bind(RiderAuthDao.class).to(RiderAuthDaoImpl.class);
		
		bind(TextManager.class).to(TextManagerImpl.class);
		bind(RoundManager.class).to(RoundManagerImpl.class);
	
		bind(HttpClient.class).toProvider(HttpClientProvider.class);
		bind(MyHttpClient.class).to(MyHttpClientImpl.class);
		bind(UrlHelper.class).to(UrlHelperImpl.class);
		
		bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class);

		bind(PasswordManager.class).to(PasswordManagerImpl.class);
		bind(TimerManager.class).to(TimerManagerImpl.class);
		
		//bind(ApplicationManager.class).to(ApplicationManagerImpl.class);

		// inject logger
		// (http://code.google.com/p/google-guice/wiki/CustomInjections)
		bindListener(Matchers.any(), new LogTypeListener());


	}

	static Matcher<TypeLiteral<?>> type(final Matcher<? super Class> matcher) {
		return new AbstractMatcher<TypeLiteral<?>>() {
			public boolean matches(TypeLiteral<?> literal) {
				return matcher.matches(literal.getRawType());
			}
		};
	}
}
