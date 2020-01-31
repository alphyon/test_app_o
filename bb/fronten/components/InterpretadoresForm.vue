<template>
<div class="card overridemargin">

    <div class="card-header"><i class="fa fa-fw fa-plus-circle"></i> <strong class="customTitle">Nuevo Interpretador</strong>
        <nuxt-link :to="{ name: 'interpretadoresview' }" class="float-right btn btn-dark btn-sm">
            <list-icon size="1.5x" class="custom-class"></list-icon> Listado
        </nuxt-link>
    </div>

    <div class="card-body">
        <div class="col-sm-6">
            <div v-if="messages"></div>

            <form>
                <div class="form-group">
                    <label>Colector<span class="text-danger">*</span></label>
                    <select :class="errors.has('colector') ? 'errorborder' : ''" v-model="form.idCollector" v-validate="'required'" data-vv-name="colector" ref="colector" class="custom-select my-1 mr-sm-2 custominput" id="interpretadorcolector">
                        <option value="-1" selected>Seleccionar</option>
                        <option v-for="(collector, index) in collectorListObj" :key="index" :value="collector.id">{{ collector.name }}</option>
                    </select>
                    <span class="displayerrors">{{ errors.first('colector') }}</span>
                </div>

                <div class="form-group">
                    <label>Nombre<span class="text-danger"> *</span></label>
                    <input :class="errors.has('nombre') ? 'errorborder' : ''" type="text" v-model="form.name" v-validate="'required'" data-vv-name="nombre" ref="nombre" name="interpretadorname" id="interpretadorname" class="form-control custominput" placeholder="Nombre" required>
                    <span class="displayerrors">{{ errors.first('nombre') }}</span>
                </div>

                <div class="form-group">
                    <label>Descripción<span class="text-danger"> *</span></label>
                    <textarea :class="errors.has('descripcion') ? 'errorborder' : ''" class="form-control custominput" v-model="form.description" v-validate="'required'" data-vv-name="descripcion" ref="descripcion" id="interpretadordescripcion" rows="3"></textarea>
                    <span class="displayerrors">{{ errors.first('descripcion') }}</span>
                </div>

                <div class="form-group">
                    <button type="button" @click="createNewInterpretador()" id="submit" class="btn btn-elaniin">
                        <plus-circle-icon size="1.5x" class="custom-class"></plus-circle-icon> Guardar
                    </button>
                </div>
            </form>
        </div>
    </div>

</div>
</template>

<script>
import {
    PlusCircleIcon,
    ListIcon
} from 'vue-feather-icons'
export default {
    name: 'InterpretadorForm',
    components: {
        PlusCircleIcon,
        ListIcon
    },
    data() {
        return {
            form: {},
            messages: null,
            collectorListObj: [],
        }
    },

    created() {
        this.collectorList();
        console.log(this);
    },

    methods: {

        async collectorList() {
            try {
                const result = await this.$manifoldRepository.index("collectorList");
                this.collectorListObj = result.details;
            } catch (error) {
                console.error(error);
            }
        },

        async createNewInterpretador() {
            try {
                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }

                const result = await this.$interpretadoresRepository.create('store', this.form);
                if (result.status === 200) {
                    this.$swal('Exito', result.message, 'success');
                }
            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
            }

        },
    },
}
</script>
