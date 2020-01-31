<template>
<div class="card overridemargin">

    <div class="card-header">

        <div class="card-header"><i class="fa fa-fw fa-globe"></i> <strong class="customTitle">Listado de Roles</strong>

            <nuxt-link :to="{ name: 'roles' }" class="float-right btn btn-dark btn-sm">
                <plus-circle-icon size="1.5x" class="custom-class"></plus-circle-icon> Nuevo Rol
            </nuxt-link>

        </div>
        <div class="card-body">
            <div>
                <table id="rolesTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                        <tr class="bg-primary text-white">
                            <th>#</th>
                            <th>Nombre</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(roles, index) in list" :key="index">
                            <td>{{ index + 1 }}</td>
                            <td>{{ roles.name }}</td>
                            <td align="center">
                                <a data-toggle="modal" data-target="#updateRole" @click="selectObj(roles)" class="text-primary cursor-pointer">
                                    <edit-icon size="1.5x" class="custom-class"></edit-icon>
                                </a> |
                                <a @click="deleteRol(roles.id)" class="text-danger cursor-pointer">
                                    <delete-icon size="1.5x" class="custom-class"></delete-icon>
                                </a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="updateRole" tabindex="-1" role="dialog" aria-labelledby="updateRole" aria-hidden="true">
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
                            <label for="channel-name" class="col-form-label">Nombre:</label>
                            <input type="text" v-model="genericObj.name" :class="errors.has('nombre') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="nombre" ref="nombre" class="form-control custominput" id="channel-name">
                            <span class="displayerrors">{{ errors.first('nombre') }}</span>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="button" @click="updateRole()" class="btn btn-primary">Guardar</button>
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
export default {
    name: 'DisplayRoles',
    components: {
        EditIcon,
        DeleteIcon,
        PlusCircleIcon
    },
    data() {
        return {
            list: [],
            genericObj: {},
        }
    },

    mounted() {
        // $("#rolesTable").DataTable();
        this.rolesList();
    },

    methods: {
        async rolesList() {
            const result = await this.$rolesRepository.index('all');
            console.log(result);
            this.list = result;
        },

        selectObj(obj) {
            this.genericObj = obj;
        },

        async updateRole() {
            try {
                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }
                const result = await this.$rolesRepository.update('update', this.genericObj.id, this.genericObj);
                if (result.status === 200) {
                    this.$swal('Exito', result.message, 'success');
                }
                $("#updateRole").modal('hide');
            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
            }
            this.rolesList();
        },

        async deleteRol(id) {
            const willDelete = await this.$swal({
                title: 'Esta seguro',
                text: 'No se podra recuperar los datos eliminados',
                icon: 'warning',
                buttons: true,
                dangerMode: true,
            });

            if (willDelete) {
                let display = await this.deleteEventRol(id);
                if (display.status === 200) {
                    this.$swal('Eliminado', display.message, 'success');
                    this.rolesList();
                } else {
                    this.$swal('Eliminado', display.message, 'error');
                }
            } else {
                this.$swal('Cancelado', 'Operacion Cancelada', 'info');
            }

        },

        async deleteEventRol(id) {
            let success = null;
            try {
                const data = await this.$rolesRepository.delete('delete', id);
                if (data) {
                    success = data;
                }
            } catch (error) {
                console.error(erro);
            }
            return success;
        },

    },
}
</script>
