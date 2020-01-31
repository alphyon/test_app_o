<template>
<div class="card overridemargin">

    <div class="card-header"><i class="fa fa-fw fa-plus-circle"></i> <strong class="customTitle">Nuevo Permiso</strong>
        <nuxt-link :to="{ name: 'permisosview' }" class="float-right btn btn-dark btn-sm">
            <list-icon size="1.5x" class="custom-class"></list-icon> Listado
        </nuxt-link>
    </div>

    <div class="card-body">
        <div class="col-sm-6">
            <div v-if="messages"></div>

            <h5 class="card-title">Campos con <span class="text-danger">*</span> son requeridos!</h5>
            <form>
                <div class="form-group">
                    <label>Nombre<span class="text-danger">*</span></label>
                    <input type="text" v-model="form.name" :class="errors.has('nombre') ? 'errorborder' : ''" v-validate="'required'" data-vv-name="nombre" ref="nombre" name="permissionsname" id="permissionsname" class="form-control custominput" placeholder="Nombre del Permiso" required>
                    <span class="displayerrors">{{ errors.first('nombre') }}</span>
                </div>
                <div class="form-group">
                    <button type="button" @click="createPermission()" id="submit" class="btn btn-elaniin">
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
    name: 'PermisosForm',
    components: {
        PlusCircleIcon,
        ListIcon
    },
    data() {
        return {
            form: {},
            messages: null,
        }
    },

    methods: {
        async createPermission() {
            try {
                let validateForm = await this.$validator.validate();
                if (!validateForm) {
                    this.$swal('Verificar', "Verifique los datos", 'warning');
                    return;
                }

                const result = await this.$permisosRepository.create('create', this.form);
                if (result.status === 200) {
                    this.$swal('Exito', result.message, 'success');
                }
            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
                // console.log(error.response)
            }
        },
    },
}
</script>
