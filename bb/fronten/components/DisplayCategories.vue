<template>
<div class="card overridemargin">

    <div class="card-header">

        <div class="card-header"><i class="fa fa-fw fa-globe"></i> <strong class="customTitle">Listado de Categorias</strong>

            <nuxt-link :to="{ name: 'categorias' }" class="float-right btn btn-dark btn-sm">
                <plus-circle-icon size="1.5x" class="custom-class"></plus-circle-icon> Nueva Caegoria
            </nuxt-link>

        </div>
        <div class="card-body">
            <div>
                <table id="categoriesTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                        <tr class="customHeaderTable text-white">
                            <th>#</th>
                            <th>Nombre</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(categories, index) in list" :key="index">
                            <td>{{ index + 1 }}</td>
                            <td>{{ categories.name }}</td>
                            <td align="center">
                                <a data-toggle="modal" data-target="#updateCategory" @click="selectObj(categories)" class="text-primary cursor-pointer">
                                    <edit-icon size="1.5x" class="custom-class"></edit-icon>
                                </a>
                                <a @click="deleteCategory(categories.id)" class="text-danger cursor-pointer">
                                    <delete-icon size="1.5x" class="custom-class"></delete-icon>
                                </a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="updateCategory" tabindex="-1" role="dialog" aria-labelledby="updateCategory" aria-hidden="true">
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
                            <label for="category-name" class="col-form-label">Nombre:</label>
                            <input type="text" v-model="categoryObj.name" :class="errors.has('nombre') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="nombre" ref="nombre"  class="form-control" id="category-name">
                            <span class="displayerrors">{{ errors.first('nombre') }}</span>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="button" @click="updateCategory()" class="btn btn-primary">Guardar</button>
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
    name: 'DisplayCategories',
    components: {
        EditIcon,
        DeleteIcon,
        PlusCircleIcon
    },
    data() {
        return {
            list: [],
            categoryObj: {},
        }
    },

    mounted() {
        // $('#categoriesTable').DataTable();
        this.categorylist();
    },

    methods: {
        async categorylist() {
            const result = await this.$categoryRepository.index('categorylist');
            this.list = result.details;
        },

        selectObj(obj) {
            this.categoryObj = obj;
        },

        async updateCategory() {
            try {
                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }

                const result = await this.$categoryRepository.update('update', this.categoryObj.id, this.categoryObj);
                if (result.status === 200) {
                    this.$swal('Exito', result.message, 'success');
                    $("#updateCategory").modal('hide');
                }
                this.categorylist();
            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
            }

        },

        async deleteCategory(id) {
            const willDelete = await this.$swal({
                title: 'Esta seguro',
                text: 'No se podra recuperar los datos eliminados',
                icon: 'warning',
                buttons: true,
                dangerMode: true,
            });

            if (willDelete) {
                let display = await this.deleteEventCategory(id);
                if (display.status === 200) {
                    this.$swal('Eliminado', display.message, 'success');
                    this.categorylist();
                } else {
                    this.$swal('Eliminado', display.message, 'error');
                }
            } else {
                this.$swal('Cancelado', 'Operacion Cancelada', 'info');
            }
        },

        async deleteEventCategory(id) {
            let success = null;
            try {
                const data = await this.$categoryRepository.delete('delete', id);
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
