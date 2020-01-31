<template>
<div class="card overridemargin">

    <div class="card-header">

        <div class="card-header"><i class="fa fa-fw fa-globe"></i> <strong class="customTitle">Listado de Interpretadores</strong>

            <nuxt-link :to="{ name: 'interpretadores' }" class="float-right btn btn-dark btn-sm">
                <plus-circle-icon size="1.5x" class="custom-class"></plus-circle-icon> Nuevo Interpretador
            </nuxt-link>

        </div>
        <div class="card-body">
            <div>
                <table id="interpreterTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                        <tr class="bg-primary text-white">
                            <th>#</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(interpretadores, index) in list" :key="index">
                            <td>{{ index + 1 }}</td>
                            <td>
                                <NuxtLink :to="`/drag/${interpretadores.idCollector}`" data-toggle="tooltip" data-placement="top" title="Crear Interpretador" class="float-center" style="color:#41BBDC;text-decoration:none;" @click.native="saveInterpreterId(interpretadores.id)">
                                    {{ interpretadores.name }}
                                </NuxtLink>
                            </td>
                            <td>{{ interpretadores.description }}</td>
                            <td align="center">
                                <a data-toggle="modal" data-target="#updateInterpretador" @click="selectObj(interpretadores)" class="text-primary cursor-pointer">
                                    <edit-icon size="1.5x" class="custom-class"></edit-icon>
                                </a>
                                <!-- <a  @click="selectObj(interpretadores)" class="text-primary cursor-pointer">
                                   <more-vertical-icon size="1.5x" class="custom-class"></more-vertical-icon>
                                </a> -->
                                 <NuxtLink  :to="`/drag/${interpretadores.idCollector}`" data-toggle="tooltip" data-placement="top" title="Visualizar Interpretador" class="float-center text-primary cursor-pointer" style="color:#000;text-decoration:none;" @click.native="updateInterpreter(interpretadores.id)">
                                   <more-vertical-icon size="1.5x" class="custom-class"></more-vertical-icon>
                                </NuxtLink>
                                <!-- <a @click="deleteInterpretador(interpretadores.id)" class="text-danger cursor-pointer"><i class="fa fa-fw fa-trash"></i> Delete</a> -->
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="updateInterpretador" tabindex="-1" role="dialog" aria-labelledby="updateInterpretador" aria-hidden="true">
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
                            <input type="text" v-model="genericObj.name" :class="errors.has('nombre') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="nombre" ref="nombre" name="interpretadorname" id="interpretadorname" class="form-control custominput" placeholder="Nombre" required>
                            <span class="displayerrors">{{ errors.first('nombre') }}</span>
                        </div>

                        <div class="form-group">
                            <label>Descripción<span class="text-danger"> *</span></label>
                            <textarea class="form-control custominput" v-model="genericObj.description" :class="errors.has('descripcion') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="descripcion" ref="descripcion" id="interpretadordescripcion" rows="3"></textarea>
                            <span class="displayerrors">{{ errors.first('descripcion') }}</span>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="button" @click="updateInterpretador()" class="btn btn-primary">Guardar</button>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import {
    EditIcon,
    PlusCircleIcon,
    MoreVerticalIcon
} from 'vue-feather-icons'
export default {
    name: 'DisplayInterpretadores',
    components: {
        EditIcon,
        PlusCircleIcon,
        MoreVerticalIcon 
    },
    data() {
        return {
            list: [],
            genericObj: {},
            collectorListObj: [],
        }
    },

    mounted() {
        // $("#interpreterTable").DataTable();
        localStorage.removeItem("opc");
        this.interpretadoresList();
        this.collectorList();
         $('[data-toggle="tooltip"]').tooltip();
    },

    methods: {

        saveInterpreterId(id){
            localStorage.setItem("idInterpreter", id);
            localStorage.setItem("opc", "create");
        },

        updateInterpreter(id){
            localStorage.setItem("idInterpreter", id);
            localStorage.setItem("opc", "update");
        },

        async interpretadoresList() {
            const result = await this.$interpretadoresRepository.index('interpretadoresList');
            this.list = result.details;
        },

        selectObj(obj) {
            this.genericObj = obj;
        },

        async collectorList() {
            try {
                const result = await this.$manifoldRepository.index("collectorList");
                this.collectorListObj = result.details;
            } catch (error) {
                console.error(error);
            }
        },

        async updateInterpretador() {
            try {
                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }

                const result = await this.$interpretadoresRepository.update('update', this.genericObj.id, this.genericObj);
                if (result.status === 200) {
                    this.$swal('Exito', result.message, 'success');
                }
                $("#updateInterpretador").modal('hide');
            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
            }
            this.interpretadoresList();
        },

        async deleteInterpretador(id) {
            this.$swal({
                title: 'Esta seguro',
                text: 'No se podra recuperar los datos eliminados',
                type: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Aceptar',
                cancelButtonText: 'Cancelar',
                showCloseButton: true,
                showLoaderOnConfirm: true
            }).then((result) => {
                if (result.value) {
                    let el = this;
                    let display = el.deleteEventRol(id);
                    if (display != null) {
                        this.$swal('Eliminado', "Datos Eliminados", 'success');
                        el.rolesList();
                    } else {
                        this.$swal('Eliminado', "Ocurrio un error", 'error');
                    }
                } else {
                    this.$swal('Cancelado', 'Operacion Cancelada', 'info');
                }
            });
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
