<template>
<div class="card overridemargin">
    <div class="card-header"><i class="fa fa-fw fa-plus-circle"></i> <strong class="customTitle">Nuevo Usuario</strong>
        <nuxt-link :to="{ name: 'usuariosview' }" class="float-right btn btn-dark btn-sm">
            <list-icon size="1.5x" class="custom-class"></list-icon> Listado
        </nuxt-link>
    </div>

    <div class="card-body">
        <div class="col-sm-6 ">
            <div v-if="messages"></div>

            <h5 class="card-title">Campos con <span class="text-danger">*</span> son requeridos!</h5>
            <br>
            <!-- <div v-if="messagesList.length > 0" class="alert alert-danger alert-dismissible fade show" role="alert">
                <ul>
                    <li v-for="errors in messagesList" :key="errors.id" class="error-messages">{{ errors.msg }}</li>
                </ul>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div> -->
            <form>
                <div class="form-group">
                    <label>Nommbre<span class="text-danger"> *</span></label>
                    <input type="text" v-model="form.name" :class="errors.has('nombre') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="nombre" ref="nombre" name="username" id="username" class="form-control custominput" placeholder="Nombre" required>
                    <span class="displayerrors">{{ errors.first('nombre') }}</span>
                </div>

                <div class="form-group">
                    <label>Apellido<span class="text-danger"> *</span></label>
                    <input type="text" v-model="form.lastname" :class="errors.has('apellido') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="apellido" ref="apellido" name="userlastname" id="userlastname" class="form-control custominput" placeholder="Apellido" required>
                    <span class="displayerrors">{{ errors.first('apellido') }}</span>
                </div>

                <div class="form-group">
                    <label>Correo<span class="text-danger"> *</span></label>
                    <input type="email" v-model="form.email" :class="errors.has('correo') ? 'errorborder' : ''" v-validate="'required|email'" data-vv-name="correo" ref="correo" name="useremail" id="useremail" class="form-control custominput" placeholder="Email" required>
                    <span class="displayerrors">{{ errors.first('correo') }}</span>
                </div>

                <div class="form-group">
                    <label>Usuario<span class="text-danger"> *</span></label>
                    <input type="text" v-model="form.username" :class="errors.has('usuario') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="usuario" ref="usuario" name="user" id="user" class="form-control custominput" placeholder="Usuario" required>
                    <span class="displayerrors">{{ errors.first('usuario') }}</span>
                </div>

                <div class="form-group">
                    <label>Contraseña<span class="text-danger"> *</span></label>
                    <input type="password" v-model="form.password" :class="errors.has('contraseña') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="contraseña" ref="contraseña" name="userpassword" id="userpassword" class="form-control custominput" placeholder="Contraseña" required>
                    <span class="displayerrors">{{ errors.first('contraseña') }}</span>
                </div>

                <!-- activo -->
                <div class="form-group">
                    <label>Activo<span class="text-danger"> *</span></label>
                    <input type="checkbox" v-model="form.enabled" name="Activo" id="useractive"><br>
                </div>

                <div class="form-group">
                    <label>Roles<span class="text-danger"> *</span></label>
                    <multiselect v-model="form.roles" :class="errors.has('roles') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="roles" ref="roles" :options="rolesListObj" tag-placeholder="Prueba" placeholder="Seleccione rol" label="name" track-by="id" :multiple="true" :taggable="true" @tag="addTag"></multiselect>
                    <span class="displayerrors">{{ errors.first('roles') }}</span>
                </div>

                <div class="form-group">
                    <button type="button" @click="createNewUser()" id="submit" class="btn btn-elaniin">
                        <plus-circle-icon size="1.5x" class="custom-class"></plus-circle-icon>Guardar
                    </button>
                </div>
            </form>
        </div>
    </div>

</div>
</template>

<script>
import Multiselect from 'vue-multiselect'
import {
    PlusCircleIcon,
    ListIcon
} from 'vue-feather-icons'
export default {
    name: 'ChannelsForm',
    components: {
        Multiselect,
        PlusCircleIcon,
        ListIcon
    },
    data() {
        return {
            form: {},
            messages: null,
            rolesListObj: [],
            messagesList: [],
        }
    },

    created() {
        this.rolesList();
    },

    methods: {
        async rolesList() {
            try {
                const result = await this.$rolesRepository.index("all");
                this.rolesListObj = result;
            } catch (error) {
                console.error(error);
            }
        },

        async createNewUser() {
            try {

                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }

                const result = await this.$usuariosRepository.create('create', this.form);
                if (result.status === 200) {
                    this.$swal('Exito', result.message, 'success');
                }
            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
            }

        },

        addTag(newTag) {
            const tag = {
                name: newTag,
                code: newTag.substring(0, 2) + Math.floor((Math.random() * 10000000))
            }
            this.rolesListObj.push(tag);
        }

    },
}
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style><style>
.multiselect__tags {
    min-height: 40px;
    display: block;
    padding: 8px 40px 0 8px;
    border-radius: 5px;
    border: 1px solid #e8e8e8;
    background: #fff;
    font-size: 14px;
    border: 1px solid #aebac9;
    box-sizing: border-box;
    border-radius: 10px;
}
</style>
