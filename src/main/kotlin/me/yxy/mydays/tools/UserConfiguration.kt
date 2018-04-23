package me.yxy.mydays.tools

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "user")
class UserConfiguration{

    var daysLimit:Int = 0

}