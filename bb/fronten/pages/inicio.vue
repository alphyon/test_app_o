<template>
<div>
    <p>Inicio de Nitro </p>
</div>
</template>

<script>
export default {
    data() {
        return {
            form: {},
        }
    },

    created() {
        this.hola();
    },

    methods: {
        async hola() {
            console.log(this.$store);
        }
    }
}
</script>
