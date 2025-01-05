import { Button } from "@chakra-ui/react"

const AddIcon = () => "+";

const DrawerForm = () => {
    return (
        <Button
            leftIcon = {<AddIcon/>}
            colorScheme={"teal"}
            onClick={() => alert("create new customer")}
            >
            Create customer
        </Button>
    )
}

export default DrawerForm;