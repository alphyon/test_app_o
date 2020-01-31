<template>
<div class="container">
    <MainfoldForm />
</div>
</template>

<script>
import MainfoldForm from '~/components/MainfoldForm.vue'

export default {
    middleware: 'auth',
    components: {
        MainfoldForm
    }
}
</script>
