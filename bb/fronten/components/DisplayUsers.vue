<template>
<div class="card overridemargin">

    <div class="card-header">

        <div class="card-header"><i class="fa fa-fw fa-globe"></i> <strong class="customTitle">Listado de Usuarios</strong>

            <nuxt-link :to="{ name: 'usuarios' }" class="float-right btn btn-dark btn-sm">
                <plus-circle-icon size="1.5x" class="custom-class"></plus-circle-icon> Nuevo Usuario
            </nuxt-link>

        </div>
        <div class="card-body">
            <div>
                <table id="usuariosTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                        <tr class="bg-primary text-white">
                            <th>#</th>
                            <th>Nombre Completo</th>
                            <th>Correo</th>
                            <th>Usuario</th>
                            <th>Estado</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(users, index) in list" :key="index">
                            <td>{{ index + 1 }}</td>
                            <td>{{ users.name }} {{ users.lastname }}</td>
                            <td>{{ users.email }}</td>
                            <td>{{ users.username }}</td>
                            <td> <span :class="users.enabled ? 'badge badge-pill badge-success' : 'badge badge-pill badge-danger'">{{ users.enabled ? 'Activo' : 'Inactivo' }}</span> </td>
                            <td align="center">
                                <a data-toggle="modal" data-target="#updateUsers" @click="selectObj(users)" class="text-primary cursor-pointer">
                                    <edit-icon size="1.5x" class="custom-class"></edit-icon>
                                </a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="updateUsers" tabindex="-1" role="dialog" aria-labelledby="updateUsers" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Actualizar</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label>Nommbre<span class="text-danger"> *</span></label>
                            <input type="text" v-model="genericObj.name" :class="errors.has('nombre') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="nombre" ref="nombre" name="username" id="username" class="form-control custominput" placeholder="Nombre" required>
                            <span class="displayerrors">{{ errors.first('nombre') }}</span>
                        </div>

                        <div class="form-group">
                            <label>Apellido<span class="text-danger"> *</span></label>
                            <input type="text" v-model="genericObj.lastname" :class="errors.has('apellido') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="apellido" ref="apellido" name="userlastname" id="userlastname" class="form-control custominput" placeholder="Apellido" required>
                            <span class="displayerrors">{{ errors.first('apellido') }}</span>
                        </div>

                        <div class="form-group">
                            <label>Email<span class="text-danger"> *</span></label>
                            <input type="email" v-model="genericObj.email" :class="errors.has('correo') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="correo" ref="correo" name="useremail" id="useremail" class="form-control custominput" placeholder="Email" required>
                            <span class="displayerrors">{{ errors.first('correo') }}</span>
                        </div>

                        <div class="form-group">
                            <label>Usuario<span class="text-danger"> *</span></label>
                            <input type="text" v-model="genericObj.username" :class="errors.has('usuario') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="usuario" ref="usuario" name="user" id="user" class="form-control custominput" placeholder="Usuario" required>
                            <span class="displayerrors">{{ errors.first('usuario') }}</span>
                        </div>
                        <div class="form-group">
                            <label>Roles<span class="text-danger"> *</span></label>
                            <multiselect v-model="genericObj.roles" :class="errors.has('roles') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="roles" ref="roles" :options="rolesListObj" tag-placeholder="Prueba" placeholder="Seleccione rol" label="name" track-by="id" :multiple="true" :taggable="true" @tag="addTag"></multiselect>
                            <span class="displayerrors">{{ errors.first('roles') }}</span>
                        </div>
                        <!-- activo -->
                        <div class="form-group">
                            <label>Activo<span class="text-danger"> *</span></label>
                            <input type="checkbox" v-model="genericObj.enabled" name="Activo" id="useractive">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="button" @click="updateUser()" class="btn btn-primary">Guardar</button>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import {
    EditIcon,
    DeleteIcon,
    PlusCircleIcon
} from 'vue-feather-icons'
import Multiselect from 'vue-multiselect'

export default {
    name: 'DisplayRoles',
    components: {
        Multiselect,
        EditIcon,
        DeleteIcon,
        PlusCircleIcon
    },
    data() {
        return {
            list: [],
            genericObj: {},
            rolesListObj: [],
        }
    },

    mounted() {
        // $("#usuariosTable").DataTable();
        this.usersList();
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

        async usersList() {
            const result = await this.$usuariosRepository.index('all');
            this.list = result;
        },

        async selectObj(obj) {
            const result = await this.$usuariosRepository.show('readuser', obj.id);
            this.genericObj = result.details;
        },

        async updateUser() {
            try {
                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }
                const result = await this.$usuariosRepository.update('update', this.genericObj.id, this.genericObj);
                if (result.status === 200) {
                    this.$swal('Exito', result.message, 'success');
                }
                $("#updateUsers").modal('hide');
            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
            }
            this.usersList();
        },

        addTag(newTag) {
            const tag = {
                name: newTag,
                code: newTag.substring(0, 2) + Math.floor((Math.random() * 10000000))
            }
            this.channelListObj.push(tag);
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
