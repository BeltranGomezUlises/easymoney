import React from 'react';
import {
    Form, Button, Input, Grid,
    Message, Card, Checkbox
} from 'semantic-ui-react';
import * as utils from '../../../utils.js';
import FileUploader from '../../FileUploader.jsx';

export default class EntityForm extends React.Component {

    constructor(props) {
        super(props);
        // dias de la semana que aplica multa empezando por lunes
        let checks = [true, true, true, true, true, true, true];
        this.state = {
            loading: false,
            warningMessage: null,
            element: {
                nombre: '',
                direccion: '',
                telefono: '',
                apodo: '',
                diasSinMulta: '',
                imagen: null
            },
            checks
        }
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit() {
        this.setState({ loading: true });

        let diasSinMulta = '';
        for (var i = 0; i < this.state.checks.length; i++) {
            if (!this.state.checks[i]) {
                if (diasSinMulta === '') {
                    diasSinMulta += i;
                } else {
                    diasSinMulta += ',' + i;
                }
            }
        }

        let { element } = this.state;
        element.diasSinMulta = diasSinMulta;

        fetch(localStorage.getItem('url') + 'clientes', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': localStorage.getItem('tokenSesion')
            },
            body: JSON.stringify(element)
        }).then((res) => res.json())
            .then((r) => {
                this.setState({ loading: false });
                utils.evalResponse(r, () => {
                    this.props.closeModal();
                    this.props.load();
                }, 'Cliente agregado con éxito');
            })
    }

    updateDiasSinMulta(indiceDia) {
        let { checks } = this.state;
        checks[indiceDia] = !checks[indiceDia];
        this.setState({ checks });
    }

    renderNombresClientes() {
        let { clientes } = this.props;        
        if (clientes) {
            if (clientes.length > 0) {
                return (
                    clientes.map(c => {
                        return <option value={c.nombre} />
                    })
                );
            }
        }
    }

    renderWarningMessage() {
        if (this.state.warningMessage != null) {
            return (
                <Message warning attached='bottom'>
                    <h4>{this.state.warningMessage}</h4>
                </Message>
            )
        }
    }

    renderImage(imageName) {
        if (imageName !== null && imageName !== '') {
            let route = localStorage.getItem('url') + 'utilerias/getFile/' + imageName;
            return (
                <Card image={route} />
            )
        }
    }

    render() {
        return (
            <div>
                {this.renderWarningMessage()}
                <Form onSubmit={this.handleSubmit}>

                    <Grid celled='internally'>
                        <Grid.Row>
                            <Grid.Column width={5}>

                                <label>Foto:</label>
                                {this.renderImage(this.state.element.imagen)}
                                <FileUploader uploaded={(fileName) => {
                                    let { element } = this.state;
                                    element.imagen = fileName;
                                    this.setState({ element });
                                }} />

                            </Grid.Column>
                            <Grid.Column width={11}>

                                <Form.Field>
                                    <label>Nombre:</label>
                                    <input
                                        required
                                        placeholder='Nombre del cliente...'
                                        list="nombreClientes"
                                        value={this.state.element.nombre}
                                        onChange={(evt) => {
                                            let { element } = this.state;
                                            element.nombre = evt.target.value;
                                            this.setState({ element });
                                        }} />
                                    <datalist id='nombreClientes'>
                                        {this.renderNombresClientes()}
                                    </datalist>
                                </Form.Field>
                                
                                <Form.Field control={Input} required label='Apodo:'
                                    type='text' placeholder='Apodo del cliente...' maxLength='50'
                                    value={this.state.element.apodo}
                                    onChange={(evt) => {
                                        let { element } = this.state;
                                        element.apodo = evt.target.value;
                                        this.setState({ element });
                                    }}
                                >
                                </Form.Field>
                                <Form.Field control={Input} required label='Teléfono:'
                                    type='text' placeholder='Teléfono del cliente...' maxLength='10'
                                    value={this.state.element.telefono}
                                    onChange={(evt) => {
                                        let { element } = this.state;
                                        element.telefono = evt.target.value;
                                        this.setState({ element });
                                    }}
                                >
                                </Form.Field>
                                <Form.Field control={Input} required label='Apodo:'
                                    type='text' placeholder='Dirección del cliente...' maxLength='250'
                                    value={this.state.element.direccion}
                                    onChange={(evt) => {
                                        let { element } = this.state;
                                        element.direccion = evt.target.value;
                                        this.setState({ element });
                                    }}
                                >
                                </Form.Field>
                                <label>Días que aplica multa:</label>
                                <Form.Field>
                                    <Checkbox
                                        label='Lunes'
                                        checked={this.state.checks[1]}
                                        onChange={(evt, data) => {
                                            this.updateDiasSinMulta(1);
                                        }} />
                                    <label></label>
                                    <Checkbox
                                        label='Martes'
                                        checked={this.state.checks[2]}
                                        onChange={(evt, data) => {
                                            this.updateDiasSinMulta(2);
                                        }} />
                                    <label></label>
                                    <Checkbox
                                        label='Miercoles'
                                        checked={this.state.checks[3]}
                                        onChange={(evt, data) => {
                                            this.updateDiasSinMulta(3);
                                        }} />
                                    <label></label>
                                    <Checkbox
                                        label='Jueves'
                                        checked={this.state.checks[4]}
                                        onChange={(evt, data) => {
                                            this.updateDiasSinMulta(4);
                                        }} />
                                    <label></label>
                                    <Checkbox
                                        label='Viernes'
                                        checked={this.state.checks[5]}
                                        onChange={(evt, data) => {
                                            this.updateDiasSinMulta(5);
                                        }} />
                                    <label></label>
                                    <Checkbox
                                        label='Sabado'
                                        checked={this.state.checks[6]}
                                        onChange={(evt, data) => {
                                            this.updateDiasSinMulta(6);
                                        }} />
                                    <label></label>
                                    <Checkbox
                                        label='Domingo'
                                        checked={this.state.checks[0]}
                                        onChange={(evt, data) => {
                                            this.updateDiasSinMulta(0);
                                        }} />
                                </Form.Field>

                            </Grid.Column>
                        </Grid.Row>
                    </Grid>








                    <br></br>
                    <Button color='green'
                        loading={this.state.loading}
                        type={this.state.loading ? 'button' : 'submit'}>
                        Agregar
                    </Button>
                </Form>
            </div>
        );
    }

}