<template>
<div class="row">
    <div class="col-md-12 overridemargin">
        <!-- <h2 class="text-center mb-5">Bootstrap 4 Register Form</h2> -->
        <div class="row">
            <div class="col-md-6 mx-auto">
                <div class="card ">
                    <div class="card-header">
                        <h3 class="mb-0 my-2">Registro de Usuario</h3>
                    </div>
                    <div class="card-body">
                        <form class="form" role="form" autocomplete="off">
                            <div class="form-group">
                                <label for="inputName">Nombre</label>
                                <input type="text" v-model="form.name" class="form-control custominput" id="registerName" placeholder="Nombre">
                            </div>
                            <div class="form-group">
                                <label for="inputEmail3">Apellido</label>
                                <input type="text" v-model="form.lastname" class="form-control custominput" id="registerLastName" placeholder="Apellido" required="">
                            </div>
                            <div class="form-group">
                                <label for="inputEmail3">Correo</label>
                                <input type="email" v-model="form.email" class="form-control custominput" id="registerMail" placeholder="Correo" required="">
                            </div>
                            <div class="form-group">
                                <label for="inputEmail3">Usuario</label>
                                <input type="text" v-model="form.username" class="form-control custominput" id="registerUsername" placeholder="Usuario" required="">
                            </div>
                            <div class="form-group">
                                <label for="inputPassword3">Contraseña</label>
                                <input type="password" v-model="form.password" class="form-control custominput" id="registerPassword" placeholder="password" title="6 caràcteres con números y letras" required="">
                            </div>
                            <div class="form-group">
                                <label for="inputVerify3">Verificar Contraseña</label>
                                <input type="password" v-model="form.verify" class="form-control custominput" id="registerVerify" placeholder="Verificar Contraseña" required="">
                            </div>
                            <div class="form-group">
                                <button type="button" @click="createNewUser()" class="btn btn-success btn-lg float-right">Registrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!--/row-->

    </div>
    <!--/col-->
</div>
<!--/row-->
</template>

<script>
export default {
    data() {
        return {
            form: {},
            messages: null,
        }
    },

    methods: {

        async createNewUser() {
            if (this.form.password !== this.form.verify) {
                this.$swal('Error', "Las contraseñas no son iguales", 'warning');
                return;
            }
            try {
                const result = await this.$usuariosRepository.create('register', this.form);
                if (result.status === 200) {
                    this.$swal('Exito', result.message, 'success');
                    this.$auth.redirect("login");
                }
            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
            }

        },
    }
}
</script>
