package me.yxy.mydays

fun common(a:Int){
    println(a)
}


class FuncTest{

    infix fun says(str:String){
        common(123)
        println("Your input is $str")
    }


    fun findout(action:(content:String,num:Int) -> Boolean) : String{
        return if(action("3",2)){
            "Yes"
        }else{
            "No"
        }
    }
}

fun main(args: Array<String>) {
    FuncTest() says "hahhahaha"

    val result = FuncTest().findout({content, num ->
        content.toInt() == num
    })
    println(result)

}


