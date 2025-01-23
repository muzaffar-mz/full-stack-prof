import {
    AlertDialog,
    AlertDialogBody,
    AlertDialogContent, AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogOverlay, Button
} from "@chakra-ui/react";
import React from "react";
import {deleteCustomer} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";

const DeleteCustomerAlertDialog = ({ isOpen, onClose, id, fetchCustomers }) => {
    const cancelRef = React.useRef()

    const handleDelete = async () => {
        deleteCustomer(id)
            .then(res => {
                console.log(res);
                successNotification("Customer was deleted",
                    `Customer with id ${id} was successfully deleted`
                )
                fetchCustomers();
            }).catch(err => {
            console.log(err);
            errorNotification(err.code, err.response.data.message)
        })
    }

    return (
        <>
            {/*<Button colorScheme='red' onClick={onOpen}>*/}
            {/*    Delete Customer*/}
            {/*</Button>*/}

            <AlertDialog
                isOpen={isOpen}
                leastDestructiveRef={cancelRef}
                onClose={onClose}
            >
                <AlertDialogOverlay>
                    <AlertDialogContent>
                        <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                            Delete Customer
                        </AlertDialogHeader>

                        <AlertDialogBody>
                            Are you sure? You can't undo this action afterwards.
                        </AlertDialogBody>

                        <AlertDialogFooter>
                            <Button ref={cancelRef} onClick={onClose}>
                                Cancel
                            </Button>
                            <Button colorScheme='red' onClick={handleDelete} ml={3}>
                                Delete
                            </Button>
                        </AlertDialogFooter>
                    </AlertDialogContent>
                </AlertDialogOverlay>
            </AlertDialog>
        </>
    )
}

export default DeleteCustomerAlertDialog;