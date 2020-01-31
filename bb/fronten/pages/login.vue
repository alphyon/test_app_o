<template>
<div class="container">
    <LoginForm />
</div>
</template>login

<script>
import LoginForm from '~/components/LoginForm.vue'

export default {
    auth: 'guest',
    components: {
        LoginForm
    }
}
</script>
