<template>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <nuxt-link :to="{ name: 'inicio' }" class="navbar-brand"> NITRO </nuxt-link>
    <button v-if="this.$store.state.auth.loggedIn" class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse" v-if="this.$store.state.auth.loggedIn">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <nuxt-link :to="{ name: 'usuarios' }" class="nav-link"> Usuarios </nuxt-link>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Adm. Roles y Accesos
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <nuxt-link :to="{ name: 'roles' }" class="dropdown-item"> Roles </nuxt-link>
                    <nuxt-link :to="{ name: 'permisos' }" class="dropdown-item"> Permisos </nuxt-link>
                    <!-- <div class="dropdown-divider"></div> -->
                    <!-- <a class="dropdown-item" href="#">Something else here</a> -->
                </div>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Adm. Colectores
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <nuxt-link :to="{ name: 'categorias' }" class="dropdown-item"> Categorias </nuxt-link>
                    <nuxt-link :to="{ name: 'canales' }" class="dropdown-item"> Canales </nuxt-link>
                    <div class="dropdown-divider"></div>
                    <nuxt-link :to="{ name: 'colectores' }" class="dropdown-item"> Colectores </nuxt-link>
                </div>
            </li>

            <li class="nav-item">
                <nuxt-link :to="{ name: 'interpretadores' }" class="nav-link"> Interpretadores </nuxt-link>
            </li>
            <!--
            <li class="nav-item">
                <nuxt-link :to="{ name: 'drag' }" class="nav-link"> Drag build </nuxt-link>
            </li>
            -->
        </ul>
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit" @click="logOut()">Cerrar Sesión</button>
    </div>
    <nuxt-link v-if="!this.$store.state.auth.loggedIn" :to="{ name: 'registro' }" class="float-right btn btn-dark btn-sm btn btn-outline-success my-2 my-sm-0"> Registro
    </nuxt-link>
</nav>
</template>

<script>
export default {
    name: 'NavBar',

    data() {
        return {

        }
    },

    created() {},

    methods: {
        async logOut() {
            await this.$auth.logout();
            this.$auth.redirect("login");
        },
    }
}
</script>
