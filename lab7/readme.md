<h1>Лабораторная работа №7</h1>
<p>Целью данной лабораторной работи есть изучение работы с широковещательными сообщениями. В программе должно быть реализовано обработку таких событий: низкий заряд батареи, режим в самолёте и нажатие на кнопку камеры. Реализовать вывод сообщения сначала через диалоговые окна, а потом и через уведомления</p>
<h2>Выполнение задания №1</h2>
<h3>Класс приёмник</h3>
<p>Создадим классы приёмников для каждого события: <b>BatteryReceiver</b>, <b>BatteryReceiver</b>, <b>BatteryReceiver</b></p>
<p>изучим приёмник на примере <b>BatteryReceiver</b>:</p>

```kotlin
class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BATTERY_LOW) {
            val i = Intent(context, DialogActivity::class.java)
            i.putExtra("dialog_id", 1)
            context?.startActivity(i)
        }
    }

}
```
<p>В методе <b>onReceive</b> проверяем, произошло ли то событие, на которое нам следует отреагировать, в данном случае <b>ACTION_BATTERY_LOW</b>. Реакцией на определённое событие должен быть вызов диалогового окна, но диалоговое окно можно вызвать только из Активити, поэтому создаем намерение Intent для перехода к DialogActivity, внутри которого будет вызвано соответствующее окно.</p>

<h3>Регистрация приёмников</h3>
<p>Также для того, чтобы приёмники могли работать, их нужно зарегистрировать. Сделаем это в контексте в MainActivity. В методе <b>onStart</b> регистрируем 3 приемника. Также для регистрации приёмнику нужен соответствующий фильтр для реакции на определенные события:</p>

```kotlin
private val airplaneReceiver = AirplaneModeReceiver()
private val batteryReceiver = BatteryReceiver()
private val cameraButtonReceiver = CameraButtonReceiver()

override fun onStart() {
    super.onStart()
    val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
    val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_LOW)
    val cameraButtonFilter = IntentFilter(Intent.ACTION_CAMERA_BUTTON)

    registerReceiver(airplaneReceiver, filter)
    registerReceiver(batteryReceiver, batteryFilter)
    registerReceiver(cameraButtonReceiver, cameraButtonFilter)
}
```
<p>Также в методе <b>onStop</b> снимаем приемники с регистрации:</p>

```korlin
override fun onStop() {
    super.onStop()
    unregisterReceiver(airplaneReceiver)
    unregisterReceiver(batteryReceiver)
    unregisterReceiver(cameraButtonReceiver)
}
```
<h3>Диалоговые окна</h3>
<p>Теперь посмотрим на наши диалоговые окна. Они нужны лишь для оповещения пользователя о том, что какое-то событие произошло. Самым интересным является диалоговое окно смены режима "в самолёте". С помощью Settings.System получаем текущее положение режима, и, в зависимости от этого, выводим сообщение о том, включён ли режим "в самолёте" или нет. Также по нажатию на кнопку ОК вызывается функция <b>goBack</b> для возвращения в MainActivity</p>

```kotlin
class AirplaneModeDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if(Settings.System.getInt(context?.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0){
            AlertDialog.Builder(requireActivity())
                .setTitle("Airplane mode info")
                .setMessage("Airplane mode active")
                .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ -> goBack()
                })
                .create()
        }else{
            AlertDialog.Builder(requireActivity())
                .setTitle("Airplane mode info")
                .setMessage("Airplane Mode inactive")
                .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ -> goBack()
                })
                .create()
        }
    }
    private fun goBack() {
        val i = Intent(context, MainActivity::class.java)
        startActivity(i)
    }
}
```
<h3>DialogActivity</h3>
<p>Как было сказано ранее, диалоговые окна вызываются из <b>DialogActivity</b>. Внутри Активити проверяется значение <b>dialog_id</b>, в зависимости от значения которого вызывается то или иное диалоговое окно. Из приемника низкой зарядки отправляется значение 1, режима в самолете - 2, и из нажатия на кнопку - 3.</p>

```kotlin
class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secont_activity)
        val i = intent
        val id = i.getIntExtra("dialog_id", 0)
        val dialog: DialogFragment
        when (id) {
            1 -> {
                dialog = BatteryDialog()
                dialog.show(supportFragmentManager, "batteryDlg")
            }
            2 -> {
                dialog = AirplaneModeDialog()
                dialog.show(supportFragmentManager, "AirplaneDlg")
            }
            3 -> {
                dialog = CameraButtonDialog()
                dialog.show(supportFragmentManager, "CameraDlg")
            }
        }
    }

}
```
<h3>Результат работы програмы</h3>
<p>Для начала включим режим в самолёте. Как видим, диалоговое окно успешно вызвано</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab7/images/dialog1.jpg" width="300"></img>
<p>После этого выключаем этот режим и видим соответствующий результат:</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab7/images/dialog2.jpg" width="300"></img>
<p>При низком заряде будет вызвано такое сообщение:</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab7/images/dialog3.jpg" width="300"></img>
<p>При нажатии на кнопку камеры будет вызвано:</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab7/images/dialog4.jpg" width="300"></img>
<h2>Выполнение задания №2</h2>
<p>Далее необходимо изменить программу таким образом, чтобы вместо диалоговых окон выводились уведомления</p>
<h3>Notification channels</h3>
<p>Для создания уведомлений нужно создать каналы оповещения. Сделать это можно, к примеру, в методе <b>onCreate</b> в MainActivity. Создаем каналы <b>batteryInfoChannel</b>, <b>airplaneModeChannel</b>, <b>cameraButtonChannel</b>. Для каждого указываем его идентификатор, имя и важность</p>

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val batteryInfoChannel =
            NotificationChannel("1", "Battery info", NotificationManager.IMPORTANCE_LOW)
        val airplaneModeChannel =
            NotificationChannel("2", "Airplane mode info", NotificationManager.IMPORTANCE_HIGH)
        val cameraButtonChannel =
            NotificationChannel("3", "Camera button info", NotificationManager.IMPORTANCE_HIGH)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(batteryInfoChannel)
        notificationManager.createNotificationChannel(airplaneModeChannel)
        notificationManager.createNotificationChannel(cameraButtonChannel)

        }

    }
```
<h3>Изменения в приёмнике</h3>
<p>Вызывать оповещения можно напрямую из класса приёмника, что даёт возможность избавиться от <b>DialogActivity</b>. Посмотрим на приёмник на примере <b>BatteryInfoReceiver</b>. Теперь, в методе <b>onReceive</b> создаётся Builder, в котором задаётся содержимое оповещения: иконка, заголовок, текст и важность оповещения. После этого вызывается само оповещение:</p>

```kotlin
class BatteryInfoReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BATTERY_LOW) {
            val builder = context?.let {
                NotificationCompat.Builder(it, "1")
                    .setSmallIcon(com.google.android.material.R.drawable.ic_clock_black_24dp)
                    .setContentTitle("Battery info")
                    .setContentText("LOW BATTERY")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }

            context?.let {
                NotificationManagerCompat.from(it).apply {
                    builder?.let { it1 -> this.notify(Random.nextInt(), it1.build()) }
                }
            }
        }
    }
}
```
<p>Также посмотрим на приёмник режима в самолёте. Как видим, в данном случае можем узнать текущее положение режима с помощью <b>intent.getBooleanExtra</b></p>

```kotlin
class AirplaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val builder = context?.let {
                NotificationCompat.Builder(it, "2")
                    .setContentTitle("AirplaneMode INFO")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }

            if (intent.getBooleanExtra("state", false)
            ) {
                builder?.setSmallIcon(com.google.android.material.R.drawable.ic_mtrl_checked_circle)
                builder?.setContentText("AirplaneMode active")
            } else {
                builder?.setSmallIcon(com.google.android.material.R.drawable.ic_mtrl_chip_close_circle)
                builder?.setContentText("AirplaneMode inactive")
            }

            context?.let {
                NotificationManagerCompat.from(it).apply {
                    builder?.let { it1 -> this.notify(Random.nextInt(), it1.build()) }
                }
            }
        }
    }
}
```
<h3>Результат работы програмы</h3>
<p>Проверим, как работают оповещения. Для начала включим режим "в самолёте". Получаем правильное оповещение:</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab7/images/notification1.jpg" width="300"></img>
<p>После выключения режима появляется уведомление:</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab7/images/notification2.jpg" width="300"></img>
<p>Уведомление о низком заряде</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab7/images/notification3.jpg" width="300"></img>
<p>Уведомление о нажитии на кнопку камеры:</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab7/images/notification4.jpg" width="300"></img>




