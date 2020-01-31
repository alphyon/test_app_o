<template>
<div class="container">
    <div class="d-flex justify-content-center h-100 mg-top">
        <div class="card ">
            <div class="card-header">
                <h3>NITRO</h3>
            </div>
            <div class="card-body">
                <form class="needs-validation">
                    <div v-if="messagesList.length > 0" class="alert alert-danger alert-dismissible fade show" role="alert">
                        <ul>
                            <li v-for="errors in messagesList" :key="errors.id" class="error-messages">{{ errors.msg }}</li>
                        </ul>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                <user-icon size="1.5x" class="custom-class"></user-icon>
                            </span>
                        </div>
                        <input type="text" id="usuario" v-validate="'required'" data-vv-name="usuario" ref="usuario" v-model="form.username" class="form-control" placeholder="Usuario">
                    </div>

                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                <lock-icon size="1.5x" class="custom-class"></lock-icon>
                            </span>
                        </div>
                        <input type="password" id="contraseña" v-validate="'required'" data-vv-name="contraseña" ref="contraseña" v-model="form.password" class="form-control" placeholder="Password">
                    </div>

                    <div class="input-group form-group">
                        <!-- <div class="align-items-center">
                            <input type="checkbox"> Remember Me
                        </div> -->
                    </div>

                    <div class="form-group text-center  ">
                        <button type="button" @click="login()" class="btn btn-success col-md-10">Iniciar Sesión</button>
                    </div>

                </form>
            </div>
        </div>

    </div>
</div>
</template>

<script>
import {
    UserIcon,
    LockIcon
} from 'vue-feather-icons'
export default {
    name: 'LoginForm',
    components: {
        UserIcon,
        LockIcon
    },

    data() {
        return {
            form: {},
            messagesList: [],
        }
    },

    created() {
        console.log(this);
    },

    methods: {
        async login() {
            try {

                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    if (this.$validator.errors.items.length > 0) {
                        this.messagesList = this.$validator.errors.items;
                    }
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }

                await this.$auth.loginWith('local', {
                    data: this.form
                });
                this.$auth.redirect("home");
            } catch (error) {
                if (error.response.status === 504) {
                    this.$swal('Error', "Ocurrio un problema al conectarse al server", 'error');
                }

                if (error.response.data.status === 401) {
                    this.$swal('Verificar', "Verifique que las credenciales sean las correctas", 'warning');
                }
            }
        }
    }
}
</script>
