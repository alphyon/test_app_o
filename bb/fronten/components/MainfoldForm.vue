<template>
<div class="card overridemargin">

    <div class="card-header"><i class="fa fa-fw fa-plus-circle"></i> <strong class="customTitle">Nuevo Colector</strong>
        <nuxt-link :to="{ name: 'colectoresview' }" class="float-right btn btn-dark btn-sm">
            <list-icon size="1.5x" class="custom-class"></list-icon> Listado Colectores
        </nuxt-link>
    </div>

    <div class="card-body">
        <div class="col-sm-6">
            <div v-if="messages"></div>

            <h5 class="card-title">Campos con <span class="text-danger">*</span> son requeridos!</h5>
            <form>
                <div class="form-group">
                    <label>Nombre<span class="text-danger">*</span></label>
                    <input type="text" v-model="form.name" :class="errors.has('nombre') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="nombre" ref="nombre" name="mainfoldname" id="mainfoldname" class="form-control custominput" placeholder="Nombre del Canal" required>
                    <span class="displayerrors">{{ errors.first('nombre') }}</span>
                </div>

                <div class="form-group">
                    <label>Foto</label>
                    <div class="custom-file">
                        <input type="file" class="custom-file-input custominput" id="mainfoldfoto" required>
                        <label class="custom-file-label custominput" for="validatedCustomFile">Seleccionar</label>
                    </div>

                </div>

                <div class="form-group">
                    <label>Categoria<span class="text-danger">*</span></label>
                    <select v-model="form.categories_id" :class="errors.has('categoria') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="categoria" ref="categoria" class="custom-select my-1 mr-sm-2 custominput" id="mainfoldcategorias">
                        <option value="-1" selected>Seleccionar</option>
                        <option v-for="(categories, index) in categoriesListObj" :key="index" :value="categories.id">{{ categories.name }}</option>
                    </select>
                    <span class="displayerrors">{{ errors.first('categoria') }}</span>
                </div>

                <div class="form-group">
                    <label>Canales<span class="text-danger">*</span></label>
                    <!-- <select v-model="form.channel" class="custom-select my-1 mr-sm-2" id="mainfoldcanales">
                        <option value="-1" selected>Seleccionar</option>
                        <option v-for="(channels, index) in channelListObj" :key="index" value="channels.id">{{ channels.name }}</option>
                    </select> -->
                    <multiselect v-model="form.channels" :class="errors.has('canales') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="canales" ref="canales" :options="channelListObj" tag-placeholder="Prueba" placeholder="Seleccione un canal" label="name" track-by="id" :multiple="true" :taggable="true" @tag="addTag"></multiselect>
                    <span class="displayerrors">{{ errors.first('canales') }}</span>
                </div>

                <div class="form-group">
                    <div class="row justify-content-md-center">
                        <div class="radio ">
                            <label v-for="(standar, index) in standarList" :key="index" class="radio-inline"><input type="radio" v-model="form.options"  v-validate="'required'" data-vv-name="opciones" ref="opciones" name="optradio" :value="standar.id"> {{ standar.value }}</label>
                        </div>
                    </div>
                    <div>
                        <span class="displayerrors">{{ errors.first('opciones') }}</span>
                    </div>
                </div>

                <div class="form-group">
                    <button type="button" @click="createMainfold()" id="submit" class="btn btn-elaniin">
                        <plus-circle-icon size="1.5x" class="custom-class"></plus-circle-icon>Guardar
                    </button>
                </div>
            </form>
        </div>
    </div>

</div>
</template>

<script>
import Multiselect from 'vue-multiselect';
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
            channelListObj: [],
            categoriesListObj: [],
            standarList: [{
                id: 1,
                value: 'Estandar Diesco',
            }, {
                id: 1,
                value: 'NPE',
            }, {
                id: 1,
                value: 'Otros',
            }]
        }
    },

    created() {
        this.categoriesList();
        this.channelsList();
    },

    methods: {
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

        async imgToBase64() {
            let dataImg = "";
            let file = $("#mainfoldfoto")[0].files[0];
            const toBase64 = file => new Promise((resolve, reject) => {
                const reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = () => resolve(reader.result);
                reader.onerror = error => reject(error);
            });
            return dataImg = await toBase64(file);
        },

        async createMainfold() {
            try {
                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }

                this.form.photo = await this.imgToBase64();
                const result = await this.$manifoldRepository.create('store', this.form);
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
