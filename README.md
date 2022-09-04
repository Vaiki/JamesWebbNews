# JamesWebbNews
Привет! Данный проект создан для отслеживания новостей по теме "Космос", также возможен поиск других интересующих новостей с помощью news Api. 
Для разработки приложения применялись следующие компоненты:
- Architectural Components (Lifecycles, LiveData, ViewModel и Room Persistence)
- Coroutines
- RxJava
- Retrofit
- Koin
- Navigation Components
- WorkManager
- SharedPreferences
- BottomNavigation
- RecyclerView
- ViewBinding
- Glide
- SearchView
- Notification
- SnackBar

# Описание приложения:
В приложении реализован BottomNavigation для навигации между тремя фрагментами (Breaking News, Saved News, Search News).
Первый фрагмент (Breaking News) который отображается при запуске приложения показывает новости по теме "Космос" на текущую дату .

![image](https://user-images.githubusercontent.com/42176005/188330005-99713800-e100-4523-97c9-2b2f7d85cf82.png)

При клике по выбранной статье в любом из трех фрагментов открывается webView отображает первоисточник новости. В webView имеется два floatingActionButton
с помощью которых можно сохранить статью и/или поделиться ссылкой на статью в месседжерах.

![image](https://user-images.githubusercontent.com/42176005/188331174-331c6c21-d815-4e70-8a36-6da5374915fd.png)

На вкладке Saved News BottomNavigation отображаются сохраненные статьи. При помощи свайпа можно удалить сохраненную статью, 
при этом отобразится SnackBar который сообщит об удалении статьи с возможностью отмены удаления.

![image](https://user-images.githubusercontent.com/42176005/188331481-cc8e2fb1-51da-4ccc-ab4f-e65ffd09b675.png)
![image](https://user-images.githubusercontent.com/42176005/188331497-35546e7d-06c5-4b30-a652-085e8be93615.png)

На вкладке Search News BottomNavigation осуществляется поиск новостей по запросу с помощью SearchView, новости отсортированы по дате создания.

![image](https://user-images.githubusercontent.com/42176005/188331598-9cdb3d10-2815-469d-af2a-09c79e6b0c08.png)

При отсутствии интернет соединения отображается соответствующий Toast.

![image](https://user-images.githubusercontent.com/42176005/188331644-b8b7aae5-06f0-44b2-80e3-422e06b84131.png)

В приложении предусмотрены уведомления о новых статьях с помощью workManager.

![image](https://user-images.githubusercontent.com/42176005/188331723-95df1b02-88da-4549-abe4-cb06620caab1.png)






