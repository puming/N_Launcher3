# N_Launcher3
这是Android Open Source Project 7.0原生Launcher3。
此套源码仅供参考学习。

* 2017.03.08

## Launcher内部三个数据库
* launcher.db 包括两张表（favorites和workspaceScreens）
在LauncherProvider.DataBaseHelper的onCreate方法中初始化。
* app_icons.db 包括一张表 (icons)
在IconCache.IconDB的onCreate方法中初始化。
* widgetpreviews.db 包括一张表(shortcut_and_widget_previews)
在WidgetPreviewLoader.CacheDB的onCreate方法中初始化
## Launcher onCreate方法中初始化LauncherAppSate
```
LauncherAppState app = LauncherAppState.getInstance();
```
LauncherAppState构造方法中的初始化 LauncherModel
```
        mInvariantDeviceProfile = new InvariantDeviceProfile(sContext);
        //缓存图标
        mIconCache = new IconCache(sContext, mInvariantDeviceProfile);
        mWidgetCache = new WidgetPreviewLoader(sContext, mIconCache);

        mAppFilter = AppFilter.loadByName(sContext.getString(R.string.app_filter_class));
        //初始化LauncherMode
        mModel = new LauncherModel(this, mIconCache, mAppFilter);

        LauncherAppsCompat.getInstance(sContext).addOnAppsChangedCallback(mModel);

        // Register intent receivers
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_LOCALE_CHANGED);
        filter.addAction(SearchManager.INTENT_GLOBAL_SEARCH_ACTIVITY_CHANGED);
        // For handling managed profiles
        filter.addAction(LauncherAppsCompat.ACTION_MANAGED_PROFILE_ADDED);
        filter.addAction(LauncherAppsCompat.ACTION_MANAGED_PROFILE_REMOVED);
        filter.addAction(LauncherAppsCompat.ACTION_MANAGED_PROFILE_AVAILABLE);
        filter.addAction(LauncherAppsCompat.ACTION_MANAGED_PROFILE_UNAVAILABLE);

        sContext.registerReceiver(mModel, filter);
        UserManagerCompat.getInstance(sContext).enableAndResetCache();
        new ConfigMonitor(sContext).register();

        sContext.registerReceiver(
                new WallpaperChangedReceiver(), new IntentFilter(Intent.ACTION_WALLPAPER_CHANGED));
```
LauncherMode 异步loadWorkspace
-->LauncherAppState.getLauncherProvider().loadDefaultFavoritesIfNecessary();
loadDefaultFavoritesIfNecessary-->mOpenHelper.loadFavorites
以上过程是加载Launcher3内部默认布局。

## 卸载应用的逻辑处理

UninstallDropTarget.startUninstallActivity

