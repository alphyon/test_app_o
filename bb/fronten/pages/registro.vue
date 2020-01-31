<template>
<div class="container py-5">
    <Registro />
</div>
<!--/container-->
</template>

<script>
import Registro from '~/components/registroForm.vue'

export default {
    auth: 'guest',
    components: {
        Registro
    },
}
</script>
