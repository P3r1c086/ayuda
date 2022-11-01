package com.pedroaguilar.ayuda.entities

data class User(var id: Long = 0,
                var name: String ="",
                var surname: String ="",
                var email: String ="",
                var password: String ="",
                var address: String ="",
                var phone: String ="")
