<template>
<div class="card overridemargin">

    <div class="card-header">

        <div class="card-header"><i class="fa fa-fw fa-globe"></i> <strong class="customTitle">Listado de Colectores</strong>

            <nuxt-link :to="{ name: 'colectores' }" class="float-right btn btn-dark btn-sm">
                <plus-circle-icon size="1.5x" class="custom-class"></plus-circle-icon> Nuevo Colector
            </nuxt-link>

        </div>
        <div class="card-body">
            <div>
                <table id="colectorTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                        <tr class="bg-primary text-white">
                            <th>#</th>
                            <th>Imagen</th>
                            <th>Nombre</th>
                            <th>Categoria</th>
                            <th>Canales</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(manifold, index) in list" :key="index">
                            <td>{{ index + 1 }}</td>
                            <td><img :src="manifold.photo" :alt="manifold.name" class="img-fluid"> </td>
                            <td>{{ manifold.name }}</td>
                            <td>{{ manifold.categories.categoryName }}</td>
                            <td> <span v-for="(channels, index) in manifold.channels" :key="index" class="badge badge-pill badge-secondary">{{ channels.name }}</span> </td>
                            <td align="center">
                                <a data-toggle="modal" data-target="#updateManifold" @click="selectObj(manifold)" class="text-primary cursor-pointer">
                                    <edit-icon size="1.5x" class="custom-class"></edit-icon>
                                </a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="updateManifold" tabindex="-1" role="dialog" aria-labelledby="updateManifold" aria-hidden="true">
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
                            <input type="text" v-model="channelObj.name" :class="errors.has('nombre') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="nombre" ref="nombre" class="form-control custominput" id="channel-name">
                            <span class="displayerrors">{{ errors.first('nombre') }}</span>
                        </div>
                        <div class="form-group">
                            <label>Categoria<span class="text-danger">*</span></label>
                            <select v-model="channelObj.categories_id" :class="errors.has('categoria') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="categoria" ref="categoria" class="custom-select my-1 mr-sm-2 custominput" id="mainfoldcategorias">
                                <option value="-1" selected>Seleccionar</option>
                                <option v-for="(categories, index) in categoriesListObj" :key="index" :value="categories.id">{{ categories.name }}</option>
                                <span class="displayerrors">{{ errors.first('categoria') }}</span>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Canales<span class="text-danger">*</span></label>
                            <multiselect v-model="channelObj.channels" :class="errors.has('canales') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="canales" ref="canales" :options="channelListObj" tag-placeholder="Prueba" placeholder="Seleccione un canal" label="name" track-by="id" :multiple="true" :taggable="true" @tag="addTag"></multiselect>
                            <span class="displayerrors">{{ errors.first('canales') }}</span>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="button" @click="updateManifold()" class="btn btn-primary">Guardar</button>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import Multiselect from 'vue-multiselect'
import {
    EditIcon,
    PlusCircleIcon
} from 'vue-feather-icons'

export default {
    name: 'DisplayManifold',
    components: {
        Multiselect,
        PlusCircleIcon,
        EditIcon
    },
    data() {
        return {
            list: [],
            channelObj: {},
            channelListObj: [],
            categoriesListObj: [],
        }
    },

    mounted() {
        // $('#colectorTable').DataTable();
        this.manifoldList();
    },

    methods: {
        async manifoldList() {
            const result = await this.$manifoldRepository.index('collectorList');

            this.list = result.details;
        },

        async categoriesList() {
            try {
                const result = await this.$categoryRepository.index("categorylist");
                this.categoriesListObj = result.details;
            } catch (error) {
                console.error(error);
            }
        },

        async channelsList() {
            try {
                const result = await this.$channelRepository.index('channelList');
                this.channelListObj = result.details;
            } catch (error) {
                console.error(error);
            }
        },

        selectObj(obj) {
            this.channelObj = obj;
            this.channelsList();
            this.categoriesList();
        },

        async updateManifold() {
            try {
                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }

                const data = this.channelObj;
                console.log(data);
                const result = await this.$manifoldRepository.update('update', this.channelObj.id, data);
                if (result.status === 200) {
                    this.$swal('Exito', result.message, 'success');
                }
                $("#updateManifold").modal('hide');

            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
            }
            this.manifoldList();
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
