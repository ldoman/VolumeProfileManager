********************** Luke Doman - Volume Profile Manager README **********************

AVD for testing:
- Nexus 5 
	- API: 23
	- CPU: x86
	- Res: 1080x1920
	- Target: Google APIS

Completed:
- Creating and storing volume profiles
- Editing and deleting profiles on long click
- Enabling profile on click
- Toast notification for enabled profile

Partially completed:
- Scheduling profile activation
- Hijacking system events for profile activation
	- Receivers are defined in manifest for the following events
		- RECEIVE_BOOT_COMPLETED
		- TIME_TICK
		- HEADSET_PLUG
		- ACL_CONNECT
		- MEDIA_BUTTON

Issues I encountered:
- Storing profiles in SharedPreferences while there is a 1 mb transaction limit
	- An ArrayList of objVolumeProfiles well cleared this size and profiles were inaccessible
	- To try and avoid this, I redesigned my app to store an ArrayList of Strings in 
	SharedPreferences that contained the keys for each individual object. When I needed
	to get the profiles I searching SharedPreferences for keys of each element pulling over
	only one object at a time. This triggered the same exception due to too many 
	simultaneous queries.
	- This really ate up more time than I expected and is the reason this is incomplete.
	- Relevant reding: http://developer.android.com/intl/zh-cn/reference/android/os/TransactionTooLargeException.html


Dependencies:
- Realm 
	- Found at https://realm.io/docs/java/latest/
	- Added to build.gradle with: compile 'io.realm:realm-android:0.87.5'

Bugs:
- Alarm and In Call volume settings change to different values when editing a profile

