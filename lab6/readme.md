<h1>Лабораторная работа №6</h1>

<h2>Задание</h2>
<p>Заданием лабораторной работы №6 является создание приложения, состоящего из круглых элементов со случайным значением от 1 до 99 внутри и случайным цветом. При нажатии на каждый из них должно появиться диалоговое окно со значением элемента, на который нажали.</p>
<p>Также при выполнении работы нужно воспользоваться <b>RecyclerView</b>, <b>адаптер</b> и <b>диалоги</b>.</p>

<h2>Выполнение задания</h2>
<p>Для создания сетки из элементов списка воспользуемся <b>GridLayoutManager</b> с указанием значения 4, что обозначает количество столбцов сетки:</p>

```kotlin
binding.list.layoutManager = GridLayoutManager(this, 4)

```
<p>Для того, чтобы заполнить список элементами со случайным числом и цветом, воспользуемся объектом класса <b>Random</b>(для получения числа) и <b>java Faker</b>(для получения hex кода):</p>

```kotlin
var i = 0
while (i != 32) {
    val value = Random.nextInt(1, 99)
    val color = faker.color().hex()
    list.add(Circle(value, color))
    i++
}
adapter.submitList(list.toList())
```
<p>При инициализации адаптера в файле <b>MainActivity.kt</b> так же укажем функцию, что должна сработать при нажатии на элемент списка:</p>

```kotlin
adapter = CirclesRecyclerAdapter(layoutInflater) {
    val dialog = CircleDialog.newInstance(it.number)
    dialog.show(supportFragmentManager, "dlg")
}
```
<p>В диалоговое окно передается число, что находитя внутри элемента, на который нажали.</p>
<p>Также покажем саму функцию <b>newInstance</b> внутри <b>CircleDialog.kt</b>:</p>

```kotlin
companion object {
    fun newInstance(number: Int): CircleDialog {
        val args = Bundle()
        args.putInt("number", number)
        val fragment = CircleDialog()
        fragment.arguments = args
        return fragment
    }
}
```
<p>Диалоговое окно состоит из заголовка, сообщения и кнопки "ОК", по нажатию на которое окно закроется:</p>

```kotlin
override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return AlertDialog.Builder(requireActivity())
        .setTitle("Your number is:")
        .setMessage(number.toString())
        .setPositiveButton("Ok", null)
        .create()
}
```

<h2>Результат работы программы</h2>
<p>При открытии приложения будет создано 32 элемента списка случайного цвета, заполненых случайным числом:</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab6/images/screenshot1.jpg" width="300">
<p>При нажатии на любой из элементов будет вызвано диалоговое окно с числом, что было внутри нажатого элемента(на скриншоте показан результат после нажатия верхнего левого элемента со значением 12):</p>
<img src="https://github.com/Freestanchik/MobileDeviceProgramming/blob/main/lab6/images/screenshot2.jpg" width="300">







