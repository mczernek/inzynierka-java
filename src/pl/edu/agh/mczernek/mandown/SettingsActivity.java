package pl.edu.agh.mczernek.mandown;

import pl.edu.agh.mczernek.mandown.filter.FilterFactory;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.example.mandown.R;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {

	private ListPreference filterPref;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		setupSimplePreferencesScreen();
	}

	/**
	 * Shows the simplified settings UI if the device configuration if the
	 * device configuration dictates that a simplified, single-pane UI should be
	 * shown.
	 */
	private void setupSimplePreferencesScreen() {

		setupFilterListPreference();

	}

	private void setupFilterListPreference() {

		createFilterPreferences();

		if (filterPreferencesCreated()) {

			setupFilterPrefs();

		} else {
			disableFilterPreferences();
		}
	}

	private void disableFilterPreferences() {
		filterPref.setEnabled(false);
	}

	private void setupFilterPrefs() {
		String[] entries = FilterFactory.getFiltersNames();
		filterPref.setEntries(entries);
		String[] entryValues = new String[entries.length];
		for (int i = 0; i < entries.length; ++i) {
			entryValues[i] = Integer.toString(i);
		}
		filterPref.setEntryValues(entryValues);
		filterPref.setDefaultValue(Integer.toString(FilterFactory
				.getFilterIndex()));

		filterPref.setOnPreferenceChangeListener(filterPrefsChangeListener);

	}

	private OnPreferenceChangeListener filterPrefsChangeListener = new OnPreferenceChangeListener() {

		@Override
		public boolean onPreferenceChange(Preference pref, Object val) {
			String newValue;
			if (pref instanceof ListPreference) {
				try {
					newValue = (String) val;
					FilterFactory.setFilter(Integer.parseInt(newValue));
					return true;
				} catch (ClassCastException ex) {
					return false;
				}
			} else {
				Log.d(this.getClass().getCanonicalName(),
						"Invalid preference type!");
				return false;
			}

		}

	};

	private boolean filterPreferencesCreated() {
		return null != filterPref;
	}

	private void createFilterPreferences() {
		addPreferencesFromResource(R.xml.pref_filter);

		filterPref = (ListPreference) findPreference(getResources().getString(
				R.string.choose_filter_key));
	}

}
