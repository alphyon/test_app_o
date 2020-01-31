<template>
<div class="container">
    <LoginForm />
</div>
</template>

<script>
import LoginForm from '~/components/LoginForm.vue'

export default {
    auth: false,
    components: {
        LoginForm
    }
}
</script>
