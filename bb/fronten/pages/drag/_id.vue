<template>
<div class="container">

    <div class="row">

        <div class="sidebar-left">
            <h6 class="sidebar-title-left">Iconos</h6>
            <div class="app-container">
                <draggable class="row element-draggable" :list="list1" :group="{ name: 'people', pull: 'clone', put: false }" @change="log" :clone="handleClone">
                    <div class="col-1" v-for="(element, index) in list1" :key="element.name" :emptyInsertThreshold="500" @change="log">
                        <div class="text-center">
                            <img class="img-fluid" :src="element.img" alt="">
                        </div>
                        {{ element.name }}
                    </div>
                </draggable>
            </div>
        </div>

        <div class="col-sm">

            <div id="graphContainer">

                <div class="container-request">
                    <!-- <h3 class="text-h3-graph">Request</h3> -->
                    <div class="app-container">
                        <draggable class="row element-draggable app-container" :list="list2" group="people" @change="log" style="width:100%;height:100%;">
                            <div class="col-1" v-for="(element, index) in list2" :key="uuid(element)" :emptyInsertThreshold="500" @change="log" style="margin-left: 25px;">
                                <div class="text-center">
                                    <x-icon size="1.5x" class="custom-class" @click="removeAt(index, list2)" style="float:right;"></x-icon>
                                    <img class="mx-auto img-fluid" :src="element.img" alt="">
                                </div>
                                {{ element.name }}
                                <button v-if="element.name == 'Entrada'" type="button" class="btn btn-info" @click="openModal(element, index)">Configurar</button>
                                <button v-if="element.name == 'Endpoint'" type="button" class="btn btn-primary" @click="openModal(element, index)">Configurar</button>
                                <button v-if="element.name == 'Variables'" type="button" class="btn btn-secondary" @click="openModal(element, index)">Configurar</button>
                                <button v-if="element.name == 'Mensajes'" type="button" class="btn btn-danger" @click="openModal(element, index)">Configurar</button>
                                <button v-if="element.name == 'Default'" type="button" class="btn btn-warning" @click="openModal(element, index)">Configurar</button>
                            </div>
                        </draggable>
                    </div>
                </div>

                <div>

                    <div class="segment-middle-section">
                        <div class="segment-name">REQUEST</div>
                        <div class="extremes">
                            <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="100%" height="24px" xml:space="preserve">
                                <line x1="0%" y1="12" x2="100%" y2="12" style="stroke:#E0E0DF;stroke-width:8">
                                </line>
                            </svg>
                        </div>
                        <div class="arrow-fixed">
                            <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="90px" height="24px" xml:space="preserve">
                                <polygon fill="#E0E0DF" points="0,0 83,0 92,12 83,24 0,24 7,12">
                                </polygon>
                            </svg>
                        </div>
                    </div>
                </div>
                <!--
                <div class="segment-right-section">
                    <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="50px" height="24px" xml:space="preserve">
                            <polygon fill="#ECECEC" points="0,8 23,8 23,0 42,12 23,24 23,16 0,16 ">
                        </polygon></svg>
                </div>
                -->

                <br>
                <div class="segment-middle-section">
                    <div class="segment-name">RESPONSE</div>
                    <div class="extremes">
                        <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="100%" height="24px" xml:space="preserve">
                            <line x1="0%" y1="12" x2="100%" y2="12" style="stroke:#E0E0DF;stroke-width:8">
                            </line>
                        </svg>
                    </div>
                    <div class="arrow-fixed">
                        <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="90px" height="24px" xml:space="preserve">
                            <polygon fill="#E0E0DF" points="0,12 9,0 90,0 83,12 90,24 9,24">
                            </polygon>
                        </svg>
                    </div>
                </div>

                <div class="container-response">
                    <!-- <h3 class="text-h3-graph">Response</h3> -->
                    <div class="app-container">
                        <draggable class="row" :list="list3" group="people" @change="log" style="width:100%;height:100%;">
                            <div class="col-1" v-for="(element, index) in list3" :key="index" :emptyInsertThreshold="500" @change="log" style="margin-left: 25px;">
                                <div class="text-center">
                                    <x-icon size="1.5x" class="custom-class" @click="removeAt(index, list3)" style="float:right;"></x-icon>
                                    <img class="mx-auto img-fluid" :src="element.img" alt="">
                                </div>
                                {{ element.name }}
                                <button v-if="element.name == 'Mensajes'" type="button" class="btn btn-danger" @click="openModal(element, index)">Configurar</button>
                            </div>
                        </draggable>
                    </div>
                </div>
            </div>
        </div>
        <!--
        <div class="sidebar-right">
            <div class="half-panel panel-right">
                <h6>Output JSON:</h6>
                <textarea id="output"></textarea>
            </div>
        </div>
        -->
    </div>

    <div class="modal fade" id="setupEndpoint" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Configuración del Endpoint</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Metodo<span class="text-danger">*</span></label>
                            <select v-model="formEndpoint.method" ref="requestmethod" class="custom-select my-1 mr-sm-2 custominput" id="endpoint-requestmethod">
                                <option value="-1" selected>Seleccionar</option>
                                <option v-for="(method, index) in requestMethodList" :key="index" :value="method.name">{{ method.name }}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="endpoint-url" class="col-form-label">URL:</label>
                            <input type="text" v-model="formEndpoint.url" class="form-control" id="endpoint-url">
                        </div>
                        <div class="form-group">
                            <label for="endpoint-nodo" class="col-form-label">Nodo data:</label>
                            <input type="text" v-model="formEndpoint.nodo" class="form-control" id="endpoint-nodo">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-success" @click="testApi()">Test</button>
                        <!-- <button type="button" class="btn btn-primary" @click="saveEndpoint()">Guardar</button> -->
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="setupVars" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Configuración de Variables</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form>
                    <div class="modal-body">
                        <div>
                            <button type="button" class="btn btn-info" @click="addRowInput()">Agregar</button>
                        </div>
                        <div class="row mt-3" v-for="(input, index) in inputs" :key="index">
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Llave" :id="`var_${index}`" v-model="input.clave">
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Valor" :id="`varext_${index}`" v-model="input.valor">
                            </div>
                            <button type="button" class="btn btn-secondary" @click="deleteRow(index, inputs)">Eliminar</button>
                        </div>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-primary" @click="saveVars()">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="setUpMessages" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Configuración de Mensajeria</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form>
                    <div class="modal-body">
                        <!-- <div>
                            <button type="button" class="btn btn-info" @click="addRowInputMessages()">Agregar</button>
                        </div> -->
                        <!-- <div class="form-inline mt-3" v-for="(inputMess, index) in inputMessages" :key="index">
                            <div class="form-group mt-3">
                                <label for="recipient-mail" class="col-form-label ml-2">Correo: </label>
                                <input type="text" :id="`mail_${index}`" v-model="inputMess.mail" class="form-control ml-3">
                                <button type="button" class="btn btn-secondary ml-3" @click="deleteRowMessages(index)">Eliminar</button>
                            </div>
                            <div class="form-group mt-3">
                                <label for="recipient-message" class="col-form-label">Mensaje:</label>
                                <textarea :id="`message_${index}`" v-model="inputMess.message" class="form-control ml-3"></textarea>
                            </div>
                        </div> -->
                        <div class="form-inline mt-3">
                            <div class="form-group mt-3">
                                <label for="recipient-mail" class="col-form-label ml-2">Correo: </label>
                                <input type="text" id="mail" v-model="inputMessages.mail" class="form-control ml-3">
                                <!-- <button type="button" class="btn btn-secondary ml-3" @click="deleteRowMessages(index)">Eliminar</button> -->
                            </div>
                            <div class="form-group mt-3">
                                <label for="recipient-message" class="col-form-label">Mensaje:</label>
                                <textarea id="message" v-model="inputMessages.message" class="form-control ml-3"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-success" @click="sendMail()">Enviar Correo</button>
                        <!-- <button type="button" class="btn btn-primary" @click="saveMessages()">Guardar</button> -->
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Variables in-->
    <div class="modal fade" id="setupVarsIn" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Variables de Entrada</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form>
                    <div class="modal-body">
                        <div>
                            <button type="button" class="btn btn-info" @click="addRowInputIn()">Agregar</button>
                        </div>
                        <div class="row mt-3" v-for="(inputIn, index) in inputsVarIn" :key="index">
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Llave" :id="`varClave_${index}`" v-model="inputIn.clave">
                            </div>
                            <div class="col">
                                <input type="text" class="form-control" placeholder="Valor" :id="`varValor_${index}`" v-model="inputIn.valor">
                            </div>
                            <button type="button" class="btn btn-secondary" @click="deleteRow(index, inputsVarIn)">Eliminar</button>
                        </div>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-primary" @click="saveVarsIn()">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <br>
    <label for="endpoint-url" class="col-form-label font-weight-bold">URL: {{ this.customUrl }}</label><br>
    <div class="clearfix">
        <button class="btn btn-primary float-right" v-if="opc === 'create'" @click="storeNewOrquestrator()">Guardar Interpretador</button>
    </div>

</div>
</template>

<script>

import draggable from "vuedraggable";
import {
    XIcon
} from 'vue-feather-icons';

export default {
    name: "editor-interpretador",
    display: "editor-interpretador",
    order: 1,
    components: {
        draggable,
        XIcon
    },
    data() {
        return {
            formEndpoint: {},
            currentObj: [],
            currentIndex: [],
            currentElement: [],
            inputs: new Array(),
            customUrl: "",
            opc: localStorage.getItem("opc"),
            inputMessages: {},
            inputsVarIn: [],
            interpreterObj: [],
            list1: [{
                    name: "Entrada",
                    id: 1,
                    img: require('~/assets/img/list-in.svg'),
                    display: true,
                    configparams: []
                }, {
                    name: "Endpoint",
                    id: 2,
                    img: require('~/assets/img/cloud-10.svg'),
                    display: true,
                    configparams: []
                },
                {
                    name: "Variables",
                    id: 3,
                    img: require('~/assets/img/list--v1.svg'),
                    display: true,
                    configparams: []
                },
                {
                    name: "Mensajes",
                    id: 5,
                    img: require('~/assets/img/messages.svg'),
                    display: true,
                    configparams: []
                }
            ],
            requestMethodList: [{
                id: 1,
                name: 'GET'
            }, {
                id: 2,
                name: 'POST'
            }],

            list2: [],
            list3: []
        };
    },

    created() {
        localStorage.removeItem("stepVars");
        localStorage.removeItem("stepEndpoint");
        this.getProxyInfo();
        this.getInterpreterdata();
    },

    methods: {

        async getInterpreterdata() {
            try {
                const opc = localStorage.getItem("opc");
                const interpreterId = Number(localStorage.getItem("idInterpreter"));
                if (opc === 'update') {
                    let data = await this.$interpretadoresRepository.show("orchestratorobj", interpreterId);
                    this.orquestratorId = data.details.id;
                    this.list2 = JSON.parse(data.details.json);
                    console.log(JSON.parse(data.details.json));
                }
            } catch (ex) {
                console.log(ex.message);
            }
        },

        async getProxyInfo() {
            let protocol = location.protocol;
            let slashes = protocol.concat("//");
            let host = slashes.concat(window.location.hostname);
            let interpreterId = Number(localStorage.getItem("idInterpreter"));
            if (interpreterId) {
                const result = await this.$proxyTestRepository.show('interpretername', interpreterId);
                this.customUrl = host + ":8080/response/collector/" + interpreterId + "/" + result.details.name;
            }
        },

        async storeNewOrquestrator() {
            try {
                let params = {
                    json: JSON.stringify(this.list2),
                    endpoint: this.customUrl,
                    idCollector: this.$route.params.id,
                    idInterpreter: Number(localStorage.getItem("idInterpreter"))
                }

                const isOK = await this.$swal({
                    title: 'Esta seguro',
                    text: 'Esta seguro de guardar el interpretador',
                    icon: 'warning',
                    buttons: true,
                    dangerMode: true,
                });

                if (isOK) {
                    const result = await this.$orchestratorRepository.create('store', params);
                    if (result.status === 200) {
                        this.$swal('Exito', result.message, 'success');
                        this.$router.push({
                            name: 'interpretadoresview'
                        });
                    }
                } else {
                    this.$swal('Cancelado', 'Operacion Cancelada', 'info');
                }

            } catch (error) {
                if (error.response.data.status === 404) {
                    this.$swal('Error', error.response.data.message, 'error');
                }
            }
        },

        async sendMail() {
            try {

                if (!this.validEmail(this.inputMessages.mail)) {
                    this.$swal("Error en Correo", "Correo no valido", "error");
                    return false;
                }

                var constructMessage = null;
                if (this.inputs.length > 0) {
                    constructMessage = this.inputs;
                }

                const request = {
                    idCollector: this.$route.params.id,
                    url: this.customUrl,
                    icons: this.list2,
                    correo: this.inputMessages.mail,
                    mensaje: this.inputMessages.message,
                    variables: constructMessage
                };
                // console.log(request);
                const result = await this.$sendMailRepository.create('mail', request);
                $("#setUpMessages").modal('hide');
                this.$swal("Información de Correo", "Correo enviando exitosamente", "info");
                localStorage.removeItem("step");
                this.inputMessages = {};

            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },

        getIdInterpreter() {
            let id = 0;
            let interpreterId = Number(localStorage.getItem("idInterpreter"));
            if (interpreterId) {
                id = interpreterId;
            }
            return interpreterId;
        },

        async testApi() {
            try {
                this.saveEndpoint();
                $("#setupEndpoint").modal('hide');
                const request = {
                    id: 1,
                    type: 'request',
                    idCollector: this.$route.params.id,
                    idInterpreter: Number(localStorage.getItem("idInterpreter")),
                    params: this.list2
                };

                const result = await this.$proxyTestRepository.create('test', request);
                let jsonResult = null;
                let newId = this.createCode();
                if (result.details) {
                    this.list2.push({
                        name: "Variables",
                        added: true,
                        id: newId,
                        img: require('~/assets/img/list--v1.svg'),
                        display: true,
                        configparams: []
                    });
                    this.$swal('Exito', result.message, 'success');
                    jsonResult = JSON.parse(result.details)

                }
                let selft = this;
                let arrayInput = new Array();
                $.each(this.list2, function (key, res) {
                    if (res.name === 'Variables' && newId === res.id) {
                        $.each(jsonResult, function (key, value) {
                            arrayInput.push({
                                clave: key,
                                valor: value
                            });
                        });
                        res.configparams = arrayInput;
                    }
                });

            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },

        addRowInput() {
            try {
                this.inputs.push({
                    clave: '',
                    valor: ''
                });

            } catch (ex) {
                console.log(ex);
                this.$swal('Error', error.message, 'error');
            }
        },

        addRowInputIn() {
            this.inputsVarIn.push({
                clave: '',
                valor: '',
            })
        },

        deleteRow(index, listInputs) {
            try {
                listInputs.splice(index, 1)
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },

        addRowInputMessages() {
            try {
                this.inputMessages.push({
                    mail: '',
                    message: ''
                });
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },

        deleteRowMessages(index) {
            try {
                this.inputMessages.splice(index, 1);
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },

        handleClone(item) {
            let cloneMe = JSON.parse(JSON.stringify(item));
            this.$delete(cloneMe, 'uid');
            return cloneMe;
        },

        openModal(element, index) {
            try {
                this.currentElement = element;
                const self = this;
                switch (element.name) {
                    case "Endpoint":
                        $.each(this.list2, function (key, res) {
                            if (res.name === 'Endpoint' && element.uid === res.uid) {
                                if (!jQuery.isEmptyObject(res.configparams)) {
                                    self.formEndpoint = res.configparams
                                }
                            }
                        });
                        $("#setupEndpoint").modal('show');
                        break;
                    case "Variables":
                        $.each(this.list2, function (key, res) {
                            if (res.name === 'Variables' && element.uid === res.uid) {
                                self.inputs = res.configparams;
                            }
                        });
                        $("#setupVars").modal('show');
                        break;
                    case "Mensajes":
                        $("#setUpMessages").modal('show');
                        break;
                    case "Entrada":
                        $.each(this.list2, function (key, res) {
                            if (res.name === 'Entrada' && element.uid === res.uid) {
                                self.inputsVarIn = res.configparams;
                            }
                        });
                        $("#setupVarsIn").modal('show');
                        break;
                    default:
                        break;
                }
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },

        saveEndpoint() {
            try {
                let step = Number(localStorage.getItem("stepEndpoint"));
                if (step) {
                    step++;
                    this.currentElement.step = step;
                    this.formEndpoint.step = step;
                    localStorage.setItem("stepEndpoint", step);
                } else {
                    localStorage.setItem("stepEndpoint", 1);
                    this.currentElement.step = 1;
                    this.formEndpoint.step = 1;
                }
                let items = this.list2;
                this.currentElement.configparams = this.formEndpoint;
                var foundIndex = items.findIndex(x => x.uid === this.currentElement.uid);
                items[foundIndex] = this.currentElement;
                this.formEndpoint = {};
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },

        saveVars() {
            let params = [];
            try {
                let step = Number(localStorage.getItem("stepVars"));
                let u = 0;
                if (step) {
                    step++;
                    this.currentElement.step = step;
                    u = step;
                    localStorage.setItem("stepVars", step);
                }

                $.each(this.inputs, function (key, res) {
                    params.push({
                        clave: res.clave,
                        valor: res.valor,
                        step: u
                    })
                });

                this.currentElement.configparams = params;
                this.inputs = [];
                $("#setupVars").modal('hide');
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }

        },

        saveVarsIn() {
            let params = [];
            try {
                let step = Number(localStorage.getItem("stepVars"));
                let u = 0;
                if (step) {
                    step++;
                    this.currentElement.step = step;
                    u = step;
                    localStorage.setItem("stepVars", step);
                } else {
                    localStorage.setItem("stepVars", 1);
                    this.currentElement.step = 1;
                    u = 1;
                }

                $.each(this.inputsVarIn, function (key, res) {
                    params.push({
                        clave: res.clave,
                        valor: res.valor,
                        step: u
                    })
                });

                this.currentElement.configparams = params;
                this.inputsVarIn = [];
                $("#setupVarsIn").modal('hide');
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }

            console.log(this.currentElement);
        },

        saveMessages() {
            try {
                this.currentElement.configparams = this.inputMessages;
                this.inputMessages = [];
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },

        uuid(e) {
            try {
                if (e.uid) return e.uid;
                const key = Math.random()
                    .toString(16)
                    .slice(2);
                this.$set(e, "uid", key);
                return e.uid;
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },

        createCode() {
            let ALPHABET = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
            let ID_LENGTH = 4;
            var rtn = '';
            try {
                for (var i = 0; i < ID_LENGTH; i++) {
                    rtn += ALPHABET.charAt(Math.floor(Math.random() * ALPHABET.length));
                }
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
            return rtn;
        },

        displayJSON() {
            const request = {
                id: 1,
                type: 'request',
                params: this.list2
            };

            const response = {
                id: 1,
                type: 'request',
                params: this.list3
            };

            console.log("Parametrizacion: ");
            console.log(this.$route);
        },

        clone: function (el) {
            return {
                name: el.name + " cloned"
            };
        },

        log: function (evt) {
            window.console.log(evt);
        },

        removeAt: function (idx, listRemove) {
            try {
                console.log(listRemove);
                let step = Number(localStorage.getItem("step"));
                if (step) {
                    step--;
                    localStorage.setItem("step", step);
                } else {
                    localStorage.setItem("step", 0);
                }
                listRemove.splice(idx, 1);
            } catch (ex) {
                console.log(ex);
                this.$swal("Error", ex.message, "error");
            }
        },
        validEmail: function (email) {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(email);
        }
    }
};
</script>

<style>
#graphContainer {
    margin: 100px 10px 10px 10px;
    height: 80%;
    border: 1px dashed #aaa;
    -ms-border-radius: 10px;
    border-radius: 10px;
    background-color: rgba(250, 250, 250, 0.93);
    overflow: hidden;
    width: 98%;
    overflow-y: scroll;
    overflow-x: scroll;
}

.sidebar-left {
    margin-top: 9%;
    border: 1px dashed #aaa;
    -ms-border-radius: 10px;
    border-radius: 10px;
    background-color: rgba(250, 250, 250, 0.93);
    padding: 15px;
    background-color: #fff;
    width: 10%;
    height: 70vh;
    position: -webkit-sticky;
    position: sticky;
    top: 0;
}

.sidebar-right {
    margin-top: 9%;
    border: 1px dashed #aaa;
    -ms-border-radius: 10px;
    border-radius: 10px;
    background-color: rgba(250, 250, 250, 0.93);
    overflow-y: scroll;
    padding: 15px;
    background-color: #fff;
    width: 15%;
    height: 70vh;
    position: -webkit-sticky;
    position: sticky;
    top: 0;
}

.panel-right {
    width: 100%;
    float: center;
}

#output {
    width: 100%;
    height: 60vh;
}

.sidebar-title-left {
    text-align: center;
    border: 1px solid #111;
    border-radius: 10vh;
}

.text-h3-graph {
    margin-left: 10px;
}

.container-request {
    width: 100%;
    height: 40%;
}

.container-response {
    width: 100%;
    height: 40%;
}

.segment-middle-section {
    position: relative;
    height: 24px;
    width: calc(100% - 110px);
    width: -moz-calc(100% - 110px);
    width: -webkit-calc(100% - 110px);
    margin: 0 5px;
    margin-left: 6%;
}

.segment-middle-section .segment-name {
    position: absolute;
    color: #777;
    z-index: 1;
    background-color: transparent;
    font-family: Helvetica, sans-serif;
    left: calc(50% - 45px);
    width: 90px;
    text-align: center;
    font-size: 10px;
    letter-spacing: 1px;
    top: 3px;
}

.segment-middle-section .extremes {
    width: 100%;
    height: 24px;
}

.segment-middle-section .arrow-fixed {
    width: 90px;
    position: absolute;
    height: 24px;
    left: calc(50% - 45px);
    top: 0;
}

.segment-right-section {
    min-height: 24px !important;
    height: 24px !important;
    float: right;
}

.element-draggable {
    width: 100%;
}
</style>
